package br.com.cwi.crescer.api.service.quest;

import br.com.cwi.crescer.api.controller.dtos.request.quest.CompleteQuestRequest;
import br.com.cwi.crescer.api.domain.Quest;
import br.com.cwi.crescer.api.domain.User;
import br.com.cwi.crescer.api.domain.UserQuest;
import br.com.cwi.crescer.api.domain.enums.Category;
import br.com.cwi.crescer.api.mapper.UserQuestMapper;
import br.com.cwi.crescer.api.repository.TaskRepository;
import br.com.cwi.crescer.api.repository.UserQuestRepository;
import br.com.cwi.crescer.api.service.user.GetUserAuthenticatedService;
import br.com.cwi.crescer.api.shared.QuestKeys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Service
public class CompleteQuestService {

    private static final LocalDate CURRENT_DAY = LocalDate.now();
    private static final Long LIMIT_QUEST_IN_DAY = 1L;
    private static final Long MINIMUM_REQUISITE_FOR_QUEST_DAILY = 1L;
    private static final Long MINIMUM_REQUISITE_FOR_QUEST_ALL_CATEGORY = 6L;
    private static final Long MINIMUM_REQUISITE_FOR_QUEST_TASK_ALL_DAY_WEEK = 1L;
    private static final Long MINIMUM_REQUISITE_FOR_QUEST_TASK_ALL_DAY_MONTH = 1L;
    private static final String MESSAGE_MISSION_ALREADY_COMPLETED = "Você já recebeu a recompensa da missão";
    private static final String MESSAGE_HAS_NO_MINIMUM_REQUIREMENTS= "Você não possui os requisitos mínimos para a missão";
    private static final LocalDate LAST_DAY_MONTH_FOR_QUEST = CURRENT_DAY.withDayOfMonth(LocalDate.now().lengthOfMonth());
    private static final LocalDate LAST_DAY_WEEK_FOR_QUEST = CURRENT_DAY.with(TemporalAdjusters.lastInMonth(DayOfWeek.SATURDAY));

    @Autowired
    private GetUserAuthenticatedService getUserAuthenticatedService;
    @Autowired
    private GetQuestByIdService getQuestByIdService;
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserQuestMapper userQuestMapper;

    @Autowired
    private UserQuestRepository userQuestRepository;

    @Autowired
    private SuccessCompleteQuestService successCompleteQuestService;

    @Transactional
    public void complete(CompleteQuestRequest request) {

        User logado = getUserAuthenticatedService.get();
        Quest quest = getQuestByIdService.get(request.getId());
        UserQuest completeQuest = userQuestMapper.map(logado, quest);
        Long questAvailable = userQuestRepository.userQuestAvailable(logado.getId(), quest.getId(), CURRENT_DAY);
        if (questAvailable >= LIMIT_QUEST_IN_DAY) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, MESSAGE_MISSION_ALREADY_COMPLETED);
        }

        switch (quest.getKey()) {
            case QuestKeys.QUEST_CREATE_TASK_LIMPEZA:
            case QuestKeys.QUEST_CREATE_TASK_HIGIENE:
            case QuestKeys.QUEST_CREATE_TASK_ESTUDO:
            case QuestKeys.QUEST_CREATE_TASK_LAZER:
            case QuestKeys.QUEST_CREATE_TASK_SAUDE:
            case QuestKeys.QUEST_CREATE_TASK_TRABALHO:

                Long countTasksCreatedInCurrentDay = taskRepository.countTasksByCategoryAndScheduledDate(logado.getId(), Category.valueOf(quest.getName()), CURRENT_DAY);
                if (countTasksCreatedInCurrentDay < MINIMUM_REQUISITE_FOR_QUEST_DAILY) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MESSAGE_HAS_NO_MINIMUM_REQUIREMENTS);
                }
                successCompleteQuestService.complete(logado, completeQuest, quest);
                break;
            case QuestKeys.CREATE_TASKS_OF_ALL_CATEGORIES:
                List<String> countTasksAllCategory = taskRepository.findCategoryByUserIdAndScheduledDate(logado.getId(), CURRENT_DAY);

                if (countTasksAllCategory.size() < MINIMUM_REQUISITE_FOR_QUEST_ALL_CATEGORY) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MESSAGE_HAS_NO_MINIMUM_REQUIREMENTS);
                }
                successCompleteQuestService.complete(logado, completeQuest, quest);
                break;
            case QuestKeys.CREATE_TASK_EVERY_DAY_OF_THE_WEEK:
               List<LocalDate> userHasTasksOnAllDaysOfTheWeek = taskRepository.countTasksInCurrentWeek(logado.getId(), LAST_DAY_WEEK_FOR_QUEST);
                if (userHasTasksOnAllDaysOfTheWeek.size() < MINIMUM_REQUISITE_FOR_QUEST_TASK_ALL_DAY_WEEK) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MESSAGE_HAS_NO_MINIMUM_REQUIREMENTS);
                }
                successCompleteQuestService.complete(logado, completeQuest, quest);
                break;
            case QuestKeys.CREATE_TASKS_EVERY_DAY_OF_THE_MONTH:
               List<LocalDate> userHasTasksOnAllDaysOfTheMonth = taskRepository.findScheduleDateInCurrentMonth(logado.getId(), LAST_DAY_MONTH_FOR_QUEST);
                if (userHasTasksOnAllDaysOfTheMonth.size() < MINIMUM_REQUISITE_FOR_QUEST_TASK_ALL_DAY_MONTH) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MESSAGE_HAS_NO_MINIMUM_REQUIREMENTS);
                }
                successCompleteQuestService.complete(logado, completeQuest, quest);
                break;
            default:
                break;
        }

    }
}



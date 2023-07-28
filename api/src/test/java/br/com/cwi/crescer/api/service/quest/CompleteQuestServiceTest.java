package br.com.cwi.crescer.api.service.quest;

import br.com.cwi.crescer.api.controller.dtos.request.quest.CompleteQuestRequest;
import br.com.cwi.crescer.api.domain.Quest;
import br.com.cwi.crescer.api.domain.User;
import br.com.cwi.crescer.api.domain.enums.Category;
import br.com.cwi.crescer.api.mapper.UserQuestMapper;
import br.com.cwi.crescer.api.repository.TaskRepository;
import br.com.cwi.crescer.api.repository.UserQuestRepository;
import br.com.cwi.crescer.api.service.user.GetUserAuthenticatedService;
import br.com.cwi.crescer.api.shared.QuestKeys;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CompleteQuestServiceTest {

    @InjectMocks
    private CompleteQuestService completeQuestService;

    @Mock
    private GetUserAuthenticatedService getUserAuthenticatedService;

    @Mock
    private GetQuestByIdService getQuestByIdService;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserQuestMapper userQuestMapper;

    @Mock
    private UserQuestRepository userQuestRepository;

    @Mock
    private SuccessCompleteQuestService successCompleteQuestService;

    @Test
    @DisplayName("Deve completar uma missão com sucesso")
    void shouldCompleteQuestSuccessfully() {

        User user = new User();
        user.setId(1L);

        Quest quest = new Quest();
        quest.setId(1L);
        quest.setKey(QuestKeys.QUEST_CREATE_TASK_LIMPEZA);
        quest.setName(Category.LIMPEZA.name());

        when(getUserAuthenticatedService.get()).thenReturn(user);
        when(getQuestByIdService.get(1L)).thenReturn(quest);
        when(userQuestRepository.userQuestAvailable(user.getId(), quest.getId(), LocalDate.now())).thenReturn(0L);
        when(taskRepository.countTasksByCategoryAndScheduledDate(user.getId(), Category.valueOf(quest.getName()), LocalDate.now())).thenReturn(2L);

        // Act
        completeQuestService.complete(new CompleteQuestRequest(1L));

        // Assert
        verify(successCompleteQuestService, times(1)).complete(eq(user), any(), eq(quest));
    }
    @Test
    @DisplayName("Deve lançar exceção quando usuário já completou a missão no dia")
    void shouldThrowExceptionWhenUserAlreadyCompletedQuestOnSameDay() {
        // Arrange
        User user = new User();
        user.setId(1L);

        Quest quest = new Quest();
        quest.setId(1L);
        quest.setKey(QuestKeys.QUEST_CREATE_TASK_LIMPEZA);
        quest.setName(Category.LIMPEZA.name());

        when(getUserAuthenticatedService.get()).thenReturn(user);
        when(getQuestByIdService.get(1L)).thenReturn(quest);
        when(userQuestRepository.userQuestAvailable(user.getId(), quest.getId(), LocalDate.now())).thenReturn(1L);

        // Act + Assert
        assertThrows(ResponseStatusException.class, () -> completeQuestService.complete(new CompleteQuestRequest(1L)));
    }

    @Test
    @DisplayName("Deve lançar exceção quando usuário não cumpre requisito mínimo diário para a missão")
    void shouldThrowExceptionWhenUserDoesNotMeetMinimumDailyRequirement() {
        // Arrange
        User user = new User();
        user.setId(1L);

        Quest quest = new Quest();
        quest.setId(1L);
        quest.setKey(QuestKeys.QUEST_CREATE_TASK_LIMPEZA);
        quest.setName(Category.LIMPEZA.name());

        when(getUserAuthenticatedService.get()).thenReturn(user);
        when(getQuestByIdService.get(1L)).thenReturn(quest);
        when(userQuestRepository.userQuestAvailable(user.getId(), quest.getId(), LocalDate.now())).thenReturn(0L);
        when(taskRepository.countTasksByCategoryAndScheduledDate(user.getId(), Category.valueOf(quest.getName()), LocalDate.now())).thenReturn(0L);

        // Act + Assert
        assertThrows(ResponseStatusException.class, () -> completeQuestService.complete(new CompleteQuestRequest(1L)));
    }

}

package br.com.cwi.crescer.api.service.me;

import br.com.cwi.crescer.api.domain.User;
import br.com.cwi.crescer.api.domain.enums.StatusQuest;
import br.com.cwi.crescer.api.repository.UserQuestRepository;
import br.com.cwi.crescer.api.service.user.GetUserAuthenticatedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ShowStatusQuestService {

    private static final LocalDate CURRENT_DAY = LocalDate.now();
    private static final Long LIMIT_QUEST_IN_DAY = 1L;
    @Autowired
    private GetUserAuthenticatedService getUserAuthenticatedService;

    @Autowired
    private UserQuestRepository userQuestRepository;
    public StatusQuest show(Long questId) {
        User logado = getUserAuthenticatedService.get();

        Long questAvailable = userQuestRepository.userQuestAvailable(logado.getId(), questId, CURRENT_DAY);
        if (questAvailable >= LIMIT_QUEST_IN_DAY) {
            return StatusQuest.FINALIZADA;
        }

        return StatusQuest.EM_ANDAMENTO;
    }
}

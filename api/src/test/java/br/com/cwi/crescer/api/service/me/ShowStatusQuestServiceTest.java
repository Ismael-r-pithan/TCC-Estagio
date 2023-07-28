package br.com.cwi.crescer.api.service.me;

import br.com.cwi.crescer.api.domain.User;
import br.com.cwi.crescer.api.domain.enums.StatusQuest;
import br.com.cwi.crescer.api.repository.UserQuestRepository;
import br.com.cwi.crescer.api.service.user.GetUserAuthenticatedService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ShowStatusQuestServiceTest {

    @InjectMocks
    private ShowStatusQuestService showStatusQuestService;

    @Mock
    private GetUserAuthenticatedService getUserAuthenticatedService;

    @Mock
    private UserQuestRepository userQuestRepository;

    @Test
    @DisplayName("Deve retornar status EM_ANDAMENTO")
    void deveRetornarStatusEmAndamento() {
        Long questId = 1L;
        LocalDate currentDay = LocalDate.of(2023, 3, 28);
        User user = new User();
        user.setId(1L);
        when(getUserAuthenticatedService.get()).thenReturn(user);
        when(userQuestRepository.userQuestAvailable(user.getId(), questId, currentDay)).thenReturn(0L);

        // when
        StatusQuest result = showStatusQuestService.show(questId);

        // then
        assertEquals(StatusQuest.EM_ANDAMENTO, result);
        verify(getUserAuthenticatedService, times(1)).get();
        verify(userQuestRepository, times(1)).userQuestAvailable(user.getId(), questId, currentDay);
    }

    @Test
    @DisplayName("Deve retornar status FINALIZADA")
    void deveRetornarStatusFinalizada() {
        Long questId = 1L;
        LocalDate currentDay = LocalDate.of(2023, 3, 28);
        User user = new User();
        user.setId(1L);
        when(getUserAuthenticatedService.get()).thenReturn(user);
        when(userQuestRepository.userQuestAvailable(user.getId(), questId, currentDay)).thenReturn(1L);

        // when
        StatusQuest result = showStatusQuestService.show(questId);

        // then
        assertEquals(StatusQuest.FINALIZADA, result);
        verify(getUserAuthenticatedService, times(1)).get();
        verify(userQuestRepository, times(1)).userQuestAvailable(user.getId(), questId, currentDay);
    }
}


package br.com.cwi.crescer.api.service.quest;

import br.com.cwi.crescer.api.domain.Quest;
import br.com.cwi.crescer.api.domain.User;
import br.com.cwi.crescer.api.domain.UserQuest;
import br.com.cwi.crescer.api.repository.UserQuestRepository;
import br.com.cwi.crescer.api.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
public class SuccessCompleteQuestServiceTest {

    @InjectMocks
    private SuccessCompleteQuestService successCompleteQuestService;

    @Mock
    private UserQuestRepository userQuestRepository;

    @Mock
    private UserRepository userRepository;

    @Test
    @DisplayName("Deve completar a quest e atualizar as informações do usuário")
    void deveCompletarQuestEAtualizarUsuario() {
        User user = User.builder()
                .id(1L)
                .username("João")
                .email("joao@test.com")
                .money(10)
                .experience(50L)
                .build();

        UserQuest userQuest = UserQuest.builder()
                .id(1L)
                .user(user)
                .quest(Quest.builder()
                        .id(1L)
                        .name("Quest1")
                        .description("Descrição da Quest1")
                        .rewards(10)
                        .experience(20L)
                        .build())
                .build();

        Quest quest = userQuest.getQuest();

        successCompleteQuestService.complete(user, userQuest, quest);

        Assertions.assertEquals(LocalDate.now(), userQuest.getCompletedAt());
        Assertions.assertEquals(20, quest.getExperience());
        Assertions.assertEquals(10, quest.getRewards());
        Assertions.assertEquals(70, user.getExperience());
        Assertions.assertEquals(20, user.getMoney());
        Mockito.verify(userRepository, Mockito.times(1)).save(user);
        Mockito.verify(userQuestRepository, Mockito.times(1)).save(userQuest);
    }

}

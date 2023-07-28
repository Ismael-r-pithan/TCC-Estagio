package br.com.cwi.crescer.api.service.quest;

import br.com.cwi.crescer.api.domain.Quest;
import br.com.cwi.crescer.api.repository.QuestRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class GetQuestByIdServiceTest {

    @InjectMocks
    private GetQuestByIdService getQuestByIdService;

    @Mock
    private QuestRepository questRepository;

    @Test
    @DisplayName("Deve retornar a quest quando ela existe no banco de dados")
    void deveRetornarQuestQuandoExiste() {
        Long id = 1L;
        Quest quest = Quest.builder().id(id).name("Quest 1").description("Descrição da Quest 1").rewards(100).experience(10L).build();
        Mockito.when(questRepository.findById(id)).thenReturn(Optional.of(quest));

        Quest result = getQuestByIdService.get(id);

        Assertions.assertEquals(quest, result);
    }

    @Test
    @DisplayName("Deve lançar exceção quando a quest não existe no banco de dados")
    void deveLancarExcecaoQuandoNaoExiste() {
        Long id = 1L;
        Mockito.when(questRepository.findById(id)).thenReturn(Optional.empty());

        ResponseStatusException exception = Assertions.assertThrows(ResponseStatusException.class,
                () -> getQuestByIdService.get(id));

        Assertions.assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        Assertions.assertEquals("Quest não encontrada", exception.getReason());
    }
}

package br.com.cwi.crescer.api.service.quest;

import br.com.cwi.crescer.api.controller.dtos.response.QuestResponse;
import br.com.cwi.crescer.api.domain.Quest;
import br.com.cwi.crescer.api.mapper.QuestMapper;
import br.com.cwi.crescer.api.repository.QuestRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ListQuestServiceTest {

    @InjectMocks
    private ListQuestService listQuestService;

    @Mock
    private QuestRepository questRepository;

    @Mock
    private QuestMapper questMapper;

    @Test
    @DisplayName("Deve listar todas as quests paginadas")
    void deveListarTodasAsQuestsPaginadas() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Quest> quests = Arrays.asList(
                Quest.builder()
                        .id(1L)
                        .name("Quest 1")
                        .description("Descrição da Quest 1")
                        .rewards(100)
                        .recurring("DIARIA")
                        .build(),
                Quest.builder()
                        .id(2L)
                        .name("Quest 2")
                        .description("Descrição da Quest 2")
                        .rewards(200)
                        .recurring("SEMANAL")
                        .build()
        );
        Page<Quest> questPage = new PageImpl<>(quests, pageable, quests.size());

        when(questRepository.findAll(pageable)).thenReturn(questPage);

        List<QuestResponse> questResponseList = Arrays.asList(
                new QuestResponse(1L, "Quest 1", "Descrição da Quest 1", 100, "DIARIA"),
                new QuestResponse(2L, "Quest 2", "Descrição da Quest 2", 200, "SEMANAL")
        );
        when(questMapper.toResponse(any(Quest.class))).thenReturn(questResponseList.get(0), questResponseList.get(1));

        Page<QuestResponse> result = listQuestService.list(pageable);

        Assertions.assertEquals(questResponseList, result.getContent());
    }
}

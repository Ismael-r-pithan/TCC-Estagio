package br.com.cwi.crescer.api.service.quest;

import br.com.cwi.crescer.api.controller.dtos.response.QuestResponse;
import br.com.cwi.crescer.api.mapper.QuestMapper;
import br.com.cwi.crescer.api.repository.QuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ListQuestService {

    @Autowired
    private QuestRepository questRepository;

    @Autowired
    private QuestMapper questMapper;

    public Page<QuestResponse> list(Pageable pageable) {
        return questRepository.findAll(pageable).map(questMapper::toResponse);
    }
}

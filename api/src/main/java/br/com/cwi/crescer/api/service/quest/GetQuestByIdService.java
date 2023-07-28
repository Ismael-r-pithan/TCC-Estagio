package br.com.cwi.crescer.api.service.quest;

import br.com.cwi.crescer.api.domain.Quest;
import br.com.cwi.crescer.api.repository.QuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class GetQuestByIdService {
    @Autowired
    private QuestRepository questRepository;
    public Quest get(Long id) {
        return questRepository.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Quest não encontrada"));
    }
}

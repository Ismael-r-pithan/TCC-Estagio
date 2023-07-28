package br.com.cwi.crescer.api.controller;

import br.com.cwi.crescer.api.controller.dtos.request.quest.CompleteQuestRequest;
import br.com.cwi.crescer.api.controller.dtos.response.QuestResponse;
import br.com.cwi.crescer.api.service.quest.CompleteQuestService;
import br.com.cwi.crescer.api.service.quest.ListQuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/quests")
public class QuestController {

    @Autowired
    private ListQuestService listQuestService;

    @Autowired
    private CompleteQuestService completeQuestService;

    @GetMapping
    public ResponseEntity<Page<QuestResponse>> listQuests(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable)  {
        return ResponseEntity.status(OK).body(listQuestService.list(pageable));
    }


    @PostMapping
    public ResponseEntity<Void> completeQuest(@Valid @RequestBody CompleteQuestRequest request) {
        completeQuestService.complete(request);
        return ResponseEntity.status(OK).build();
    }

}

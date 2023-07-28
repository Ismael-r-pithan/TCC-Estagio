package br.com.cwi.crescer.api.controller;

import br.com.cwi.crescer.api.controller.dtos.request.item.BuyItemRequest;
import br.com.cwi.crescer.api.controller.dtos.request.item.CreateItemRequest;
import br.com.cwi.crescer.api.controller.dtos.response.ItemResponse;
import br.com.cwi.crescer.api.service.item.BuyItemService;
import br.com.cwi.crescer.api.service.item.CreateItemService;
import br.com.cwi.crescer.api.service.item.ListItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.io.IOException;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/items")
public class ItemController {

    @Autowired
    private ListItemsService listItemsService;

    @Autowired
    private CreateItemService createItemService;

    @Autowired
    private BuyItemService buyItemService;
    @GetMapping
    public ResponseEntity<Page<ItemResponse>> listItems(@RequestParam(required = false) String search, @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable)  {
        return ResponseEntity.status(OK).body(listItemsService.list(pageable, search));
    }

    @Secured("ROLE_ADMIN")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> createItem(@Valid CreateItemRequest createItemRequest, HttpServletRequest request) throws IOException {
        createItemService.create(createItemRequest, request);
        return ResponseEntity.status(200).build();
    }

    @PostMapping("/buy")
    public ResponseEntity<Void> buyItem(@Valid @RequestBody BuyItemRequest request) {
        buyItemService.buyItem(request);
        return ResponseEntity.status(200).build();
    }
}

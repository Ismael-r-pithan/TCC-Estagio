package br.com.cwi.crescer.api.controller;

import br.com.cwi.crescer.api.controller.dtos.request.logado.ResponseFriendshipRequest;
import br.com.cwi.crescer.api.controller.dtos.request.logado.UpdateAvatarRequest;
import br.com.cwi.crescer.api.controller.dtos.request.logado.UpdateBannerRequest;
import br.com.cwi.crescer.api.controller.dtos.response.*;
import br.com.cwi.crescer.api.domain.enums.Status;
import br.com.cwi.crescer.api.domain.enums.StatusQuest;
import br.com.cwi.crescer.api.service.me.*;
import br.com.cwi.crescer.api.service.user.GetPrincipalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/me")
public class UserLogadoController {
    @Autowired
    private GetPrincipalService getPrincipalService;
    @Autowired
    private FriendshipRequestsService friendshipRequestsService;
    @Autowired
    private RemoveFriendshipService removeFriendshipService;
    @Autowired
    private ResponseFriendshipRequestService responseFriendshipRequestService;
    @Autowired
    private ListFriendsService listFriendsService;
    @Autowired
    private UpdateAvatarService updateAvatarService;
    @Autowired
    private ListQuestsService listQuestsService;
    @Autowired
    private ListUserItemsService listUserItemsService;
    @Autowired
    private ShowStatusFriendshipService showStatusFriendshipService;
    @Autowired
    private ShowStatusQuestService showStatusQuestService;
    @Autowired
    private UpdateBannerService updateBannerService;

    @GetMapping
    public UserResponse profile() {
        return getPrincipalService.get();
    }


    @GetMapping("/friendships")
    public ResponseEntity<Page<FriendshipResponse>> listFriends(@RequestParam(required = false) String search, @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.status(OK).body(listFriendsService.list(pageable, search));
    }

    @GetMapping("/friendships/{idFriendship}/status")
    public ResponseEntity<Status> showStatusFriendship(@PathVariable Long idFriendship) {
        return ResponseEntity.status(OK).body(showStatusFriendshipService.showStatus(idFriendship));
    }

    @GetMapping("/items")
    public ResponseEntity<List<ItemResponse>> listItems() {
        return ResponseEntity.status(OK).body(listUserItemsService.list());
    }

    @GetMapping("/quests")
    public ResponseEntity<Page<UserQuestResponse>> listQuests(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.status(OK).body(listQuestsService.list(pageable));
    }

    @GetMapping("/quests/{id}/status")
    public ResponseEntity<StatusQuest> showStatusQuests(@PathVariable Long id) {
        return ResponseEntity.status(OK).body(showStatusQuestService.show(id));
    }

    @GetMapping("/friendships/requests")
    public ResponseEntity<List<FriendshipResponse>> friendshipRequests() {
        return ResponseEntity.status(OK).body(friendshipRequestsService.list());
    }

    @PutMapping("/avatar")
    public ResponseEntity<Void> updateAvatar(@Valid @RequestBody UpdateAvatarRequest request) {
        updateAvatarService.update(request);
        return ResponseEntity.status(OK).build();
    }

    @PutMapping("/banner")
    public ResponseEntity<Void> updateBanner(@Valid @RequestBody UpdateBannerRequest request) {
        updateBannerService.update(request);
        return ResponseEntity.status(OK).build();
    }

    @PutMapping("/friendships/requests")
    public ResponseEntity<Void> responseFriendshipRequest(@Valid @RequestBody ResponseFriendshipRequest request) {
        responseFriendshipRequestService.response(request);
        return ResponseEntity.status(OK).build();
    }

    @DeleteMapping("/friendships/requests/{idFriendship}")
    public ResponseEntity<Void> removeFriendship(@PathVariable Long idFriendship) {
        removeFriendshipService.remove(idFriendship);
        return ResponseEntity.status(OK).build();
    }

}

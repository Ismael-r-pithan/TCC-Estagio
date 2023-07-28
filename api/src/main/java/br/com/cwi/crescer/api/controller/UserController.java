package br.com.cwi.crescer.api.controller;

import br.com.cwi.crescer.api.controller.dtos.request.user.CreateUserRequest;
import br.com.cwi.crescer.api.controller.dtos.response.TaskResponse;
import br.com.cwi.crescer.api.controller.dtos.response.UserResponse;
import br.com.cwi.crescer.api.domain.enums.Category;
import br.com.cwi.crescer.api.domain.enums.Priority;
import br.com.cwi.crescer.api.service.me.RequestFriendshipService;
import br.com.cwi.crescer.api.service.task.ListUserTasksService;
import br.com.cwi.crescer.api.service.user.ActivateAccountService;
import br.com.cwi.crescer.api.service.user.CreateUserService;
import br.com.cwi.crescer.api.service.user.GetUserByIdService;
import br.com.cwi.crescer.api.service.user.ListUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private CreateUserService createUserService;

    @Autowired
    private ActivateAccountService activateAccountService;

    @Autowired
    private ListUsersService listUsersService;

    @Autowired
    private GetUserByIdService getUserByIdService;

    @Autowired
    private RequestFriendshipService requestFriendshipService;

    @Autowired
    private ListUserTasksService listUserTasksService;

    @PostMapping
    public ResponseEntity<UserResponse> save(@Valid @RequestBody CreateUserRequest request) {
        return ResponseEntity.status(CREATED).body(createUserService.create(request));
    }

    @GetMapping
    public ResponseEntity<Page<UserResponse>> listUsers(@RequestParam(required = false) String search, @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable)  {
        return ResponseEntity.status(OK).body(listUsersService.list(pageable, search));
    }

    @GetMapping("/activate-account")
    public ResponseEntity<Void> activateAccount(@RequestParam String token, @RequestParam String email) {
        activateAccountService.activate(token, email);
        return ResponseEntity.status(OK).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findUserById(@PathVariable Long id) {
        return ResponseEntity.status(200).body(getUserByIdService.getResponse(id));
    }

    @PostMapping("/{id}/request-friendship")
    public ResponseEntity<Void> requestFriendship(@PathVariable Long id) {
        requestFriendshipService.solicitar(id);
        return ResponseEntity.status(OK).build();
    }

    @GetMapping("/{userId}/tasks")
    public ResponseEntity<Page<TaskResponse>> listUserTasks(@PathVariable Long userId,
                                                            @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate date,
                                                            @RequestParam(required = false) Category category,
                                                            @RequestParam(required = false) Priority priority,
                                                            @PageableDefault(page = 0, size = 10, sort = "scheduledDate", direction = Sort.Direction.DESC) Pageable pageable)  {
        return ResponseEntity.status(OK).body(listUserTasksService.list(userId, date, category, priority, pageable));
    }

}

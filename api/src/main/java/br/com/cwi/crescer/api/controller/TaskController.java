package br.com.cwi.crescer.api.controller;

import br.com.cwi.crescer.api.controller.dtos.request.task.CreateTaskRequest;
import br.com.cwi.crescer.api.controller.dtos.response.TaskResponse;
import br.com.cwi.crescer.api.service.task.CreateTaskService;
import br.com.cwi.crescer.api.service.task.FinishTaskService;
import br.com.cwi.crescer.api.controller.dtos.request.task.AlterTaskRequest;
import br.com.cwi.crescer.api.service.task.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private CreateTaskService createTaskService;

    @Autowired
    private FinishTaskService finishTaskService;

    @Autowired
    private ResetTaskService resetTaskService;

    @Autowired
    private RemoveTaskService removeTaskService;

    @Autowired
    private AlterTaskService alterTaskService;

    @Autowired
    private GetTaskService getTaskService;

    @PostMapping
    public ResponseEntity<TaskResponse> save(@Valid @RequestBody CreateTaskRequest request) {
        return ResponseEntity.status(CREATED).body(createTaskService.create(request));
    }

    @PutMapping("/{taskId}/finish")
    public ResponseEntity<Void> finish(@PathVariable Long taskId) {
        finishTaskService.finish(taskId);
        return ResponseEntity.status(OK).build();
    }

    @PutMapping("/{taskId}/reset-finish")
    public ResponseEntity<Void> resetFinish(@PathVariable Long taskId) {
        resetTaskService.resetFinish(taskId);
        return ResponseEntity.status(OK).build();
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> remove(@PathVariable Long taskId) {
        removeTaskService.remove(taskId);
        return ResponseEntity.status(OK).build();
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<Void> alter(@PathVariable Long taskId, @Valid @RequestBody AlterTaskRequest request) {
        alterTaskService.alter(taskId, request);
        return ResponseEntity.status(OK).build();
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskResponse> get(@PathVariable Long taskId) {
        return ResponseEntity.status(OK).body(getTaskService.getResponse(taskId));
    }

}

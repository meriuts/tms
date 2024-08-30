package ru.tms.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tms.dto.BaseResponse;
import ru.tms.dto.task.TaskDto;
import ru.tms.dto.task.CreateTaskDto;
import ru.tms.dto.task.TaskParameterDto;
import ru.tms.dto.task.UpdateTaskDto;
import ru.tms.service.TaskService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/task")
public class TaskController {

    private final TaskService taskService;

    @PostMapping("/create")
    public ResponseEntity<BaseResponse<CreateTaskDto>> createTask(@Valid @RequestBody CreateTaskDto task) {
        return new ResponseEntity<>(taskService.createTask(task), HttpStatus.CREATED);
    }

    @GetMapping("/assign-user/{user-id}/task/{task-id}")
    public ResponseEntity<BaseResponse<TaskDto>> assignUserFroTask(
            @PathVariable("user-id") Long assignUserId,
            @PathVariable("task-id") Long taskId) {
        return new ResponseEntity<>(taskService.assignUserFroTask(assignUserId, taskId), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<TaskDto>> getTaskById(@PathVariable Long id) {
        return new ResponseEntity<>(taskService.getTaskById(id), HttpStatus.OK);
    }

    @GetMapping("/by-assigned/{user-id}")
    public ResponseEntity<BaseResponse<TaskDto>> getAllTaskFotAssignedByUserId(
            @PathVariable("user-id") Long id) {
        return new ResponseEntity<>(taskService.getAllTaskForAssignedByUserId(id), HttpStatus.OK);
    }

    @GetMapping("/by-author/{user-id}")
    public ResponseEntity<BaseResponse<TaskDto>> getAllTaskFotAuthorByUserId(
            @PathVariable("user-id") Long id) {
        return new ResponseEntity<>(taskService.getAllTaskForAuthorByUserId(id), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<BaseResponse<TaskDto>> updateTask(@Valid @RequestBody UpdateTaskDto task) {
        return new ResponseEntity<>(taskService.updateTask(task), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<BaseResponse<TaskDto>> deleteTask(@PathVariable Long id) {
        return new ResponseEntity<>(taskService.deleteTask(id), HttpStatus.OK);

    }

    @GetMapping("/parameter")
    public ResponseEntity<BaseResponse<TaskParameterDto>> readTaskParameter() {
        return new ResponseEntity<>(taskService.readTaskParameter(), HttpStatus.OK);

    }

}

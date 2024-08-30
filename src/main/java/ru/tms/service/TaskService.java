package ru.tms.service;

import ru.tms.dto.BaseResponse;
import ru.tms.dto.task.TaskDto;
import ru.tms.dto.task.UpdateTaskDto;
import ru.tms.dto.task.CreateTaskDto;
import ru.tms.dto.task.TaskParameterDto;

public interface TaskService {
    BaseResponse<CreateTaskDto> createTask(CreateTaskDto createTaskDto);
    BaseResponse<TaskDto> assignUserFroTask(Long assignUserId, Long taskId);
    BaseResponse<TaskDto> getTaskById(Long taskId);
    BaseResponse<TaskDto> getAllTaskForAuthorByUserId(Long userId);
    BaseResponse<TaskDto> getAllTaskForAssignedByUserId(Long userId);
    BaseResponse<TaskDto> updateTask(UpdateTaskDto updateTaskDto);
    BaseResponse<TaskDto> deleteTask(Long taskId);
    BaseResponse<TaskParameterDto> readTaskParameter();
}

package ru.tms.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ru.tms.dto.BaseResponse;
import ru.tms.dto.task.CreateTaskDto;
import ru.tms.dto.task.TaskDto;
import ru.tms.dto.task.TaskParameterDto;
import ru.tms.dto.task.UpdateTaskDto;
import ru.tms.exception.BusinessException;
import ru.tms.mapper.TaskMapper;
import ru.tms.model.task.TaskEntity;
import ru.tms.model.task.TaskPriority;
import ru.tms.model.task.TaskStatus;
import ru.tms.model.user.UserEntity;
import ru.tms.repository.AssignedTasksRepository;
import ru.tms.repository.TaskRepository;
import ru.tms.repository.UserRepository;

import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final AssignedTasksRepository assignedTasksRepository;
    private final TaskMapper taskMapper;

    @Override
    public BaseResponse<CreateTaskDto> createTask(CreateTaskDto createTaskDto) {
        try {
            UserEntity author = userRepository.findById(createTaskDto.getAuthor()).orElseThrow();

            TaskEntity taskEntity = taskMapper.mapToTaskEntity(createTaskDto);
            taskEntity.setAuthor(author);
            TaskEntity savedTask = taskRepository.save(taskEntity);

            CreateTaskDto response = taskMapper.mapToCreateTaskDto(savedTask);
            response.setAuthor(author.getId());

            return new BaseResponse<>(null, Arrays.asList(response));

        } catch (DataIntegrityViolationException ex) {
            throw new BusinessException(
                    MessageFormat.format("Задача с title: {0} уже существует", createTaskDto.getTaskTitle()));
        } catch (NoSuchElementException ex) {
            throw new BusinessException(
                    MessageFormat.format("Пользователь с id {0} не существует",
                            String.valueOf(createTaskDto.getAuthor())));
        }
    }

    @Override
    public BaseResponse<TaskDto> assignUserFroTask(Long assignUserId, Long taskId) {
        try {
            UserEntity assignUser = userRepository.findById(assignUserId).orElseThrow();
            TaskEntity taskEntity = taskRepository.findById(taskId).orElseThrow();
            taskEntity.getUsersAssigned().add(assignUser);

            TaskDto taskDto = taskMapper.mapToTaskDto(taskRepository.save(taskEntity));

            return new BaseResponse<>(null, Arrays.asList(taskDto));

        } catch (DataIntegrityViolationException ex) {
            throw new BusinessException(
                    MessageFormat.format("Пользователь с id {0} уже назначен на задачу с id {1}",
                            String.valueOf(assignUserId), String.valueOf(taskId)));
        } catch (NoSuchElementException ex) {
            throw new BusinessException("Указан неверный id пользователя или задачи");
        }

    }

    @Override
    public BaseResponse<TaskDto> getTaskById(Long id) {
        try {
            TaskEntity taskEntity = taskRepository.findById(id).orElseThrow();

            return new BaseResponse<>(null, List.of(taskMapper.mapToTaskDto(taskEntity)));

        } catch (NoSuchElementException ex) {
            throw new BusinessException(
                    MessageFormat.format("Задача с id {0} не существует", String.valueOf(id)));
        }
    }

    @Override
    public BaseResponse<TaskDto> getAllTaskForAuthorByUserId(Long userId) {
        try {
            UserEntity userEntity = userRepository.findById(userId).orElseThrow();
            List<TaskEntity> tasks = taskRepository.findByAuthorId(userEntity.getId());

            List<TaskDto> taskDtoList = new ArrayList<>();
            for (TaskEntity taskEntity : tasks) {
                TaskDto taskDto = taskMapper.mapToTaskDto(taskEntity);
                taskDtoList.add(taskDto);
            }

            return new BaseResponse<>(null, taskDtoList);

        } catch (NoSuchElementException ex) {
            throw new BusinessException(
                    MessageFormat.format("Пользователя с id {0} не существует", String.valueOf(userId)));
        }
    }

    @Override
    public BaseResponse<TaskDto> getAllTaskForAssignedByUserId(Long userId) {
        try {
            List<Long> assignedUserTasks = assignedTasksRepository.findAllTaskIdByUserId(userId);

            List<TaskEntity> tasks = taskRepository.findAllById(assignedUserTasks);

            List<TaskDto> taskDtoList = new ArrayList<>();
            for (TaskEntity taskEntity : tasks) {
                TaskDto taskDto = taskMapper.mapToTaskDto(taskEntity);
                taskDtoList.add(taskDto);
            }

            return new BaseResponse<>(null, taskDtoList);

        } catch (NoSuchElementException ex) {
            throw new BusinessException(
                    MessageFormat.format("Пользователя с id {0} не существует", String.valueOf(userId)));
        }
    }

    @Override
    public BaseResponse<TaskDto> updateTask(UpdateTaskDto updateTaskDto) {
        try {
            TaskEntity taskEntity = taskRepository.findById(updateTaskDto.getId()).orElseThrow();
            taskMapper.updateTaskEntityFromUpdateTaskDto(updateTaskDto, taskEntity);
            taskRepository.save(taskEntity);

            return new BaseResponse<>(null, List.of(taskMapper.mapToTaskDto(taskEntity)));

        } catch (NoSuchElementException ex) {
            throw new BusinessException(
                    MessageFormat.format("Задачи с id {0} не существует", String.valueOf(updateTaskDto.getId())));
        }

    }

    @Override
    public BaseResponse<TaskDto> deleteTask(Long taskId) {
        try {
            TaskEntity taskEntity = taskRepository.findById(taskId).orElseThrow();
            taskRepository.delete(taskEntity);

            return new BaseResponse<>(null, List.of(taskMapper.mapToTaskDto(taskEntity)));

        } catch (NoSuchElementException ex) {
            throw new BusinessException(
                    MessageFormat.format("Задачи с id {0} не существует", String.valueOf(taskId)));
        }
    }

    @Override
    public BaseResponse<TaskParameterDto> readTaskParameter() {
        Set<String> taskStatuses = Stream.of(TaskStatus.values())
                .map(TaskStatus::name)
                .collect(Collectors.toSet());

        Set<String> taskPriorities = Stream.of(TaskPriority.values())
                .map(TaskPriority::name)
                .collect(Collectors.toSet());

        BaseResponse<TaskParameterDto> taskParameter =
                new BaseResponse<>(null, Arrays.asList(new TaskParameterDto(taskStatuses, taskPriorities)));

        return taskParameter;
    }
}

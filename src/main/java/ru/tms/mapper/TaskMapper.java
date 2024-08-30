package ru.tms.mapper;

import org.mapstruct.*;
import ru.tms.dto.task.CreateTaskDto;
import ru.tms.dto.task.TaskDto;
import ru.tms.dto.task.UpdateTaskDto;
import ru.tms.dto.user.UserDto;
import ru.tms.model.task.TaskEntity;
import ru.tms.model.task.TaskPriority;
import ru.tms.model.task.TaskStatus;
import ru.tms.model.user.UserEntity;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TaskMapper {
    @Mapping(target = "author", ignore = true)
    CreateTaskDto mapToCreateTaskDto(TaskEntity taskEntity);
    @Mapping(target = "author", ignore = true)
    @Mapping(source = "taskStatus", target = "taskStatus", qualifiedByName = "stringToTaskStatus")
    @Mapping(source = "taskPriority", target = "taskPriority", qualifiedByName = "stringToTaskPriority")
    TaskEntity mapToTaskEntity(CreateTaskDto createTaskDto);

    @Mapping(target = "author", ignore = true)
    @Mapping(source = "taskStatus", target = "taskStatus", qualifiedByName = "stringToTaskStatus")
    @Mapping(source = "taskPriority", target = "taskPriority", qualifiedByName = "stringToTaskPriority")
    TaskEntity mapToTaskEntity(UpdateTaskDto updateTaskDto);

    @Mapping(source = "author", target = "author", qualifiedByName = "userEntityToUserID")
    @Mapping(source = "taskStatus", target = "taskStatus", qualifiedByName = "taskStatusToString")
    @Mapping(source = "taskPriority", target = "taskPriority", qualifiedByName = "taskPriorityToString")
    @Mapping(source = "usersAssigned", target = "assigns", qualifiedByName = "mapToUserDtoList")
    TaskDto mapToTaskDto(TaskEntity taskEntity);

    UserDto mapToUserDto(UserEntity userEntity);
    @Named("mapToUserDtoList")
    List<UserDto> mapToUserDtoList(List<UserEntity> userEntities);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateTaskEntityFromUpdateTaskDto(UpdateTaskDto dto, @MappingTarget TaskEntity taskEntity);


    @Named("userEntityToUserID")
    default Long taskStatusToString(UserEntity userEntity) {
        return userEntity.getId();
    }

    @Named("taskStatusToString")
    default String taskStatusToString(TaskStatus taskStatus) {
        return taskStatus.name();
    }

    @Named("taskPriorityToString")
    default String taskPriorityToString(TaskPriority taskPriority) {
        return taskPriority.name();
    }


    @Named("stringToTaskStatus")
    default TaskStatus stringToStatus(String status) {
        return TaskStatus.valueOf(status);
    }

    @Named("stringToTaskPriority")
    default TaskPriority stringToPriority(String priority) {
        return TaskPriority.valueOf(priority);
    }

}

package ru.tms.dto.task;

import lombok.Data;
import ru.tms.dto.user.UserDto;

import java.util.List;

@Data
public class TaskDto {
    private Long id;
    private Long author;
    private String taskTitle;
    private String taskDescription;
    private String taskStatus;
    private String taskPriority;
    private List<UserDto> assigns;

}

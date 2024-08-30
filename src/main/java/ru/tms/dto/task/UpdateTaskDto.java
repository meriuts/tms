package ru.tms.dto.task;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import ru.tms.dto.user.UserDto;

import java.util.List;

@Data
public class UpdateTaskDto {
    @NotNull(message = "Не указана id задачи для обновления")
    private Long id;
    private String taskTitle;
    private String taskDescription;
    @Pattern(regexp = "WAITING|IN_PROGRESS|DONE", message = "Доступные статусы задачи WAITING, IN_PROGRESS или DONE")
    private String taskStatus;
    @Pattern(regexp = "HIGH|MIDDLE|LOW", message = "Доступные виды приоритетов задачи HIGH, MIDDLE или LOW")
    private String taskPriority;
}

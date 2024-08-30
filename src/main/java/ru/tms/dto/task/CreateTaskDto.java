package ru.tms.dto.task;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CreateTaskDto {
    private Long id;
    @NotBlank(message = "Должен быть указан заголовок задачи")
    private String taskTitle;
    private String taskDescription;
    @Pattern(regexp = "WAITING|IN_PROGRESS|DONE", message = "Доступные статусы задачи WAITING, IN_PROGRESS или DONE")
    private String taskStatus;
    @Pattern(regexp = "HIGH|MIDDLE|LOW", message = "Доступные виды приоритетов задачи HIGH, MIDDLE или LOW")
    private String taskPriority;
    @NotNull(message = "Должен быть указан автор задачи")
    private Long author;

}

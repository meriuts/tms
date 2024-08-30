package ru.tms.dto.task;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
public class TaskParameterDto {
    private Set<String> taskStatuses;
    private Set<String> taskPriorities;
}

package ru.tms.model.task;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "t_assigned_tasks")
public class AssignedTasksEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "task_id")
    private Long taskId;
}

package ru.tms.model.task;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import ru.tms.model.user.UserEntity;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "t_tasks")
public class TaskEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Должен быть указан заголовок задачи")
    @Column(name = "task_title")
    private String taskTitle;
    @Column(name = "task_description")
    private String taskDescription;
    @Enumerated(EnumType.STRING)
    @Column(name = "task_status")
    private TaskStatus taskStatus;
    @Enumerated(EnumType.STRING)
    @Column(name = "task_priority")
    private TaskPriority taskPriority;
    @ManyToOne
    @JoinColumn(name = "task_author")
    private UserEntity author;

    @ManyToMany(cascade = CascadeType.REMOVE)
    @JoinTable(
            name = "t_assigned_tasks",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<UserEntity> usersAssigned = new ArrayList<>();


}

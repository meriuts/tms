package ru.tms.model.user;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ru.tms.model.task.TaskEntity;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "t_users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(min = 3, max = 20, message = "username должен быть от 3 до 20 символов")
    private String username;
    @Size(min = 5, max = 10, message = "password должен быть от 5 до 10 символов")
    private String password;
    @Email(message = "Указан невалидный email")
    private String email;
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TaskEntity> tasksAuthor = new ArrayList<>();
    @ManyToMany(mappedBy = "usersAssigned")
    private List<TaskEntity> tasksAssigned = new ArrayList<>();
}

package ru.tms.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateUserDto {

    @Size(min = 3, max = 20, message = "username должен быть от 3 до 20 символов")
    private String username;

    @Size(min = 5, max = 10, message = "password должен быть от 5 до 10 символов")
    private String password;
    @NotBlank(message = "Должен быть указан email пользователя")
    @Email(message = "Указан невалидный email")
    private String email;
}

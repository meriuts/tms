package ru.tms.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import ru.tms.dto.BaseResponse;
import ru.tms.dto.user.CreateUserDto;
import ru.tms.dto.user.UserDto;

public interface UserService {
    BaseResponse<UserDto> createUser(CreateUserDto user);
    BaseResponse<UserDto> getUserById(Long id);
    BaseResponse<UserDto> getUserByEmail(UserDto userDto);
}

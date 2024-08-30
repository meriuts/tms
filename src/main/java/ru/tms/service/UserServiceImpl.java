package ru.tms.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ru.tms.dto.BaseResponse;
import ru.tms.dto.user.CreateUserDto;
import ru.tms.dto.user.UserDto;
import ru.tms.exception.BusinessException;
import ru.tms.mapper.UserMapper;
import ru.tms.model.user.UserEntity;
import ru.tms.repository.UserRepository;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public BaseResponse<UserDto> createUser(CreateUserDto user) {
        try {
            UserEntity userEntity = userMapper.mapToUserEntity(user);
            UserEntity savedUser = userRepository.save(userEntity);

            return new BaseResponse<>(null, Arrays.asList(userMapper.mapToUserDto(savedUser)));

        } catch (DataIntegrityViolationException ex) {
            throw new BusinessException(
                    MessageFormat.format("Пользователь с email {0} уже существует", user.getEmail()));
        }
    }

    @Override
    public BaseResponse<UserDto> getUserById(Long id) {
        try {
            UserEntity userEntity = userRepository.findById(id).orElseThrow();

            return new BaseResponse<>(null, Arrays.asList(userMapper.mapToUserDto(userEntity)));

        } catch (NoSuchElementException ex) {
            throw new BusinessException(
                    MessageFormat.format("Пользователь с id {0} не существует", String.valueOf(id)));
        }
    }

    @Override
    public BaseResponse<UserDto> getUserByEmail(UserDto userDto) {
        try {
            UserEntity userEntity = userRepository.findByEmail(userDto.getEmail()).orElseThrow();

            return new BaseResponse<>(null, Arrays.asList(userMapper.mapToUserDto(userEntity)));

        } catch (NoSuchElementException ex) {
            throw new BusinessException(
                    MessageFormat.format("Пользователь с email {0} не существует", userDto.getEmail()));
        }
    }


}

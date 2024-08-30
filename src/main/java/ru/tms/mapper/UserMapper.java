package ru.tms.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.tms.dto.user.CreateUserDto;
import ru.tms.dto.user.UserDto;
import ru.tms.model.user.UserEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    CreateUserDto mapToCreateUserDto(UserEntity userEntity);
    UserDto mapToUserDto(UserEntity userEntity);
    UserEntity mapToUserEntity(CreateUserDto createUserDto);
}

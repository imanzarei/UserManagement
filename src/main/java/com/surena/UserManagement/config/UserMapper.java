package com.surena.UserManagement.config;

import com.surena.UserManagement.dto.UserDto;
import com.surena.UserManagement.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User userDtoToUser(UserDto userDto);

    UserDto userToUserDTO(User user);

    List<User> userDtoListToUserList(List<UserDto> userDtos);

    List<UserDto> userListToUserDto(List<User> users);
}

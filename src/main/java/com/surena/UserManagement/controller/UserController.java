package com.surena.UserManagement.controller;

import com.surena.UserManagement.config.UserMapper;
import com.surena.UserManagement.dto.UserDto;
import com.surena.UserManagement.entity.User;
import com.surena.UserManagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")


public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/")
    public UserDto saveUser(@Valid @RequestBody UserDto userDto) {
        User user = UserMapper.INSTANCE.userDtoToUser(userDto);
        userService.saveUser(user);
       return UserMapper.INSTANCE.userToUserDTO(user);
    }

    @DeleteMapping("/deleteById/{id}")
    public String deleteUserById(@PathVariable("id") Long id) {
        userService.deleteUserById(id);
        return "success";
    }

    @DeleteMapping("/deleteByUsername/{username}")
    public String deleteUserByUsername(@PathVariable("username") String username) {
        userService.deleteUserByUsername(username);
        return "success";
    }

    @GetMapping("/")
    public List<UserDto> getUsers() {
        List<User> allUsers = userService.getAllUsers();
        return UserMapper.INSTANCE.userListToUserDto(allUsers);
    }

    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable("id") Long id) {
        Optional<User> employeeById = userService.getUserById(id);
        try {
            if (employeeById.isPresent()) {
                return UserMapper.INSTANCE.userToUserDTO( employeeById.get() );
            } else
                return new UserDto();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @PutMapping("/{id}")
    public UserDto updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        User user = userService.updateUser(id, userDto);
        return UserMapper.INSTANCE.userToUserDTO(user);
    }

    @PostMapping("/changePassword")
    public UserDto changePassword(@RequestBody UserDto userDto) {
        User user = userService.changePassword(userDto);
        return UserMapper.INSTANCE.userToUserDTO(user);
    }

    @PostMapping("/findByUsername")
    public UserDto findByUsername(@RequestBody UserDto userDto) {
        return UserMapper.INSTANCE.userToUserDTO(userService.findByUsername(userDto.getUsername()));
    }

    @GetMapping("/findById/{id}")
    public UserDto findById(@PathVariable long id) {
        return UserMapper.INSTANCE.userToUserDTO(userService.findById(id));
    }

}

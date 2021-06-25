package com.surena.UserManagement.service;

import com.surena.UserManagement.dto.UserDto;
import com.surena.UserManagement.entity.User;
import com.surena.UserManagement.exceptions.DuplicateUsernameException;
import com.surena.UserManagement.exceptions.UsernameDoesNotExist;
import com.surena.UserManagement.repository.UserRepository;
import com.surena.UserManagement.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User saveUser(User user) {
        User byUsername = userRepository.findByUsername(user.getUsername());
        Optional<User> OptionalUsername = Optional.ofNullable(byUsername);
        if (OptionalUsername.isPresent())
            throw new DuplicateUsernameException("Username is Duplicate");
        user.setPassword(MD5Utils.encode(user.getPassword()));
        userRepository.save(user);
        return user;
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    public void deleteUserByUsername(String username) {
        userRepository.deleteByUsername(username);
    }

    public User updateUser(Long id, UserDto userDto) {
        Optional<User> userOptional = userRepository.findById(id);
        User user = null;
        if (userOptional.isPresent()) {
            user = userOptional.get();
            user.setFirstName(userDto.getFirstName());
            user.setLastName(userDto.getLastName());
            userRepository.save(user);
        }
        return user;
    }

    public User changePassword(UserDto userDto) {
        User byUsernameAndPassword = userRepository.findByUsernameAndPassword(userDto.getUsername(), MD5Utils.encode(userDto.getPassword()));
        Optional<User> userOptional = Optional.ofNullable(byUsernameAndPassword);
        if (userOptional.isPresent()) {
            userOptional.get().setPassword(MD5Utils.encode(userDto.getNewPassword()));
            User user = userRepository.save(byUsernameAndPassword);
            return user;
        } else {
            throw new UsernameDoesNotExist("Username or password is incorrect");
        }
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);

    }

    public User findById(Long id) {
        Optional<User> byId = userRepository.findById(id);
        if (byId.isPresent()) {
            return byId.get();
        } else
            return null;
    }
}


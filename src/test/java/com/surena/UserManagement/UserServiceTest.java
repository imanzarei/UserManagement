package com.surena.UserManagement;

import com.surena.UserManagement.dto.UserDto;
import com.surena.UserManagement.entity.User;
import com.surena.UserManagement.repository.UserRepository;
import com.surena.UserManagement.service.UserService;
import com.surena.UserManagement.util.MD5Utils;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.api.Order;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    UserRepository userRepository;

    User user;


    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        user = new User();
        user.setId(1L);
        user.setPassword("12345");
        user.setFirstName("Iman");
        user.setLastName("Zarei");
        user.setUsername("iman");
        user.setPassword(MD5Utils.encode("123456"));
    }

    @Test
    public void contextLoads() {
    }


    @Test
    @Order(1)
    public void addUserTest() {
        when(userRepository.save(ArgumentMatchers.any(User.class))).thenReturn(user);
        User created = userService.saveUser(user);
        assertEquals(created.getFirstName(), user.getFirstName());
        verify(userRepository).save(user);
    }

    @Test
    @Order(2)
    public void getUserByIdTest() {

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        User userById = userService.findById(1L);
        assertEquals("Iman", userById.getFirstName());
        assertEquals("Zarei", userById.getLastName());

    }

    @Test
    @Order(3)
    public void getUserByUsernameTest() {
        when(userRepository.findByUsername("iman")).thenReturn(user);
        User userById = userService.findByUsername("iman");
        assertEquals("Iman", userById.getFirstName());
        assertEquals("Zarei", userById.getLastName());
    }

    @Test
    @Order(4)
    public void getAllUsersTest() {
        List<User> users = new ArrayList();
        users.add(user);
        given(userRepository.findAll()).willReturn(users);
        List<User> expected = userService.getAllUsers();
        assertEquals(expected, users);
        verify(userRepository).findAll();
    }

    @Test
    @Order(5)
    public void deleteUserByIdTest() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        userService.deleteUserById(user.getId());
        verify(userRepository).deleteById(user.getId());
    }

    @Test
    @Order(6)
    public void deleteUserByUsernameTest() {
        when(userRepository.findByUsername(user.getUsername())).thenReturn(user);
        userService.deleteUserById(user.getId());
        verify(userRepository).deleteById(user.getId());
    }

    @Test
    @Order(7)
    public void updateUserTest() {
        UserDto newUser = new UserDto();
        newUser.setFirstName("test");
        newUser.setLastName("test");
        newUser.setUsername("test");
        newUser.setUsername("test");
        newUser.setPassword("123456");
        given(userRepository.findById(user.getId())).willReturn(Optional.of(user));
        userService.updateUser(user.getId(), newUser);
        verify(userRepository).findById(user.getId());

    }
}

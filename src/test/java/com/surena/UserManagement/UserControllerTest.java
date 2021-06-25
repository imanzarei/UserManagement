package com.surena.UserManagement;

import com.surena.UserManagement.config.UserMapperImpl;
import com.surena.UserManagement.controller.UserController;
import com.surena.UserManagement.dto.UserDto;
import com.surena.UserManagement.entity.User;
import com.surena.UserManagement.service.UserService;
import com.surena.UserManagement.util.MD5Utils;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.api.Order;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mapstruct.MapperConfig;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {UserMapperImpl.class})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserControllerTest {

    @InjectMocks
    UserController userController;

    @Mock
    UserService userService;

    @Autowired
    @Mock
    UserMapperImpl userMapper;


    User user;
    Map<String, Object> userMap;


    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        user = new User();
        user.setId(1L);
        user.setPassword("12345");
        user.setFirstName("Iman");
        user.setLastName("Zarei");
        user.setUsername("iman");
        userMap = new HashMap<>();
        userMap.put("firstName", "Iman");
        userMap.put("lastName", "Zarei");
        userMap.put("username", "iman1");
        userMap.put("password", MD5Utils.encode("123456"));
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
    }
    @Test
    @Order(1)
    public void addUser() {
        Response response = given()
                .contentType("application/json")
                .accept("application/json").
                        body(userMap)
                .when()
                .post("/user/").
                        then().
                        statusCode(200).
                        contentType("application/json").
                        extract().
                        response();
        Long id = response.jsonPath().getLong("id");
        assertNotNull(id);
    }

    @Test
    @Order(2)
    public void getUserById() {
        when(userService.getUserById(1L)).thenReturn(Optional.of(user));
        UserDto userDto = userController.getUser(1L);
        assertNotNull(userDto);
        assertEquals(Long.valueOf(1), userDto.getId());
        assertEquals(user.getFirstName(), userDto.getFirstName());
    }



    @Test
    @Order(3)
    public void getAllUsersTest() {
        List<User> allUsers = Arrays.asList(user);
        Response response = given()
                .contentType("application/json")
                .accept("application/json").
                        body(userMap)
                .when()
                .get("/user/").
                        then().
                        statusCode(200).
                        contentType("application/json").
                        extract().
                        response();

        List<Object> id = response.jsonPath().getList("id");
        assertNotNull(id.get(0));
    }

    @Test
    @Order(4)
    public void updateUser() {
        Response response = given()
                .contentType("application/json")
                .accept("application/json").
                        body(userMap)
                .when()
                .get("/user/").
                        then().
                        statusCode(200).
                        contentType("application/json").
                        extract().
                        response();
        List<Object> id = response.jsonPath().getList("id");
        assertNotNull(id.get(0));

    }
}
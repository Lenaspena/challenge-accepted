package com.challengeaccepted.controllers;

import com.challengeaccepted.models.User;
import com.challengeaccepted.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserControllerTest {

    private UserService userService;
    private UserRestController unitUnderTest;
    private User user;

    @Before
    public void before() throws Exception {
        userService = mock(UserService.class);
        unitUnderTest = new UserRestController(userService);
        user = setUser();
    }

    @Test
    public void createUser_ShouldReturn201() throws Exception {
        ResponseEntity<User> responseEntity = unitUnderTest.createUser(user);
        HttpStatus status = responseEntity.getStatusCode();

        assertEquals(HttpStatus.CREATED, status);
    }

    @Test
    public void readUser_ShouldReturn400() throws Exception {
        when(userService.getUserFromDatabase(1L)).thenReturn(null);

        ResponseEntity<User> responseEntity = unitUnderTest.readUser(user.getId());
        HttpStatus status = responseEntity.getStatusCode();

        assertEquals(HttpStatus.BAD_REQUEST, status);
    }

    @Test
    public void readUser_ShouldReturn200() {
        when(userService.getUserFromDatabase(1L)).thenReturn(user);

        ResponseEntity<User> responseEntity = unitUnderTest.readUser(user.getId());
        HttpStatus status = responseEntity.getStatusCode();

        assertEquals(HttpStatus.OK, status);
    }

    @Test
    public void readAllUsers_ShouldReturn200() throws Exception {

        assertEquals(HttpStatus.OK, unitUnderTest.readAllUsers().getStatusCode());
    }

    @Test
    public void testReadUserByEmail_Should_Return_Status_Code_200() throws Exception {
        assertEquals(HttpStatus.OK, unitUnderTest.readUserByEmail("david@hasselhoff.se").getStatusCode());
    }

    private User setUser() {
        user = new User();
        user.setId(1L);
        user.setFirstName("Lena");
        user.setLastName("Stridsberg");
        user.setEmail("lena.stridsberg@gmail.com");
        return user;
    }

}
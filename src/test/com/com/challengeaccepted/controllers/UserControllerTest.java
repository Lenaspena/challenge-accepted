package com.challengeaccepted.controllers;

import com.challengeaccepted.models.UserModel;
import com.challengeaccepted.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class UserControllerTest {

    @Mock
    private UserService mockedUserService;

    @Mock
    private UserModel mockedUserModel;

    @InjectMocks
    private UserController userController;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateUser_Should_Return_Status_Code_201() throws Exception {
        assertEquals("createUser() did not respond with http status 201 (created)", HttpStatus.CREATED, userController.createUser(new UserModel()).getStatusCode());
    }

    @Test
    public void testUpdateUser_Should_Return_Status_Code_200() throws Exception {
        assertEquals("updateUser() did not respond with http status 200 (ok)", HttpStatus.OK, userController.updateUser(new UserModel()).getStatusCode());
    }

    @Test
    public void testReadUser_Should_Return_Status_Code_200() throws Exception {
        when(mockedUserService.getUserFromDatabase(1L)).thenReturn(mockedUserModel);
        assertEquals("readUser() did not respond with http status 200 (ok)", HttpStatus.OK, userController.readUser(1L).getStatusCode());
        assertEquals("readUser() did not respond with http status 400 (bad request)", HttpStatus.BAD_REQUEST, userController.readUser(null).getStatusCode());
    }

    @Test
    public void testReadAllUsers_Should_Return_Status_Code_200() throws Exception {
        assertEquals("getAllUsersFromDatabase() did not respond with http status 200 (ok)", HttpStatus.OK, userController.readAllUsers().getStatusCode());
    }

    @Test
    public void testReadUserByEmail_Should_Return_Status_Code_200() throws Exception {
        assertEquals("readUserByEmail() did not respond with http status 200 (ok)", HttpStatus.OK, userController.readUserByEmail("david@hasselhoff.se").getStatusCode());
    }

}
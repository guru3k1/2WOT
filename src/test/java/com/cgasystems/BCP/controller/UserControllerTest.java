package com.cgasystems.BCP.controller;

import com.cgasystems.BCP.model.User;
import com.cgasystems.BCP.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Spy
    @InjectMocks
    UserController userController;

    @Mock
    UserService userService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getUserWithNullId(){
        String expected = "Invalid user id";

        var actual = userController.getUserById(null);

        Assertions.assertFalse(actual.getStatusOk());
        Assertions.assertEquals(expected, actual.getMessage());
        verify(userService,times(0)).getUserById(anyLong());
    }

    @Test
    public void getUserWithId1(){
        String expected = "Success";
        long testId = 1L;
        when(userService.getUserById(testId)).thenReturn(new User());

        var actual = userController.getUserById(testId);

        Assertions.assertTrue(actual.getStatusOk());
        Assertions.assertEquals(expected, actual.getMessage());
        verify(userService,times(1)).getUserById(testId);
    }
}
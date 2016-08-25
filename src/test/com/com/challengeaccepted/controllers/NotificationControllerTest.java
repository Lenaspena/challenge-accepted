package com.challengeaccepted.controllers;

import com.challengeaccepted.models.NotificationModel;
import com.challengeaccepted.services.NotificationService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.util.Random;

import static org.junit.Assert.assertEquals;

public class NotificationControllerTest {

    @Mock
    private NotificationService mockedNotificationService;

    @InjectMocks
    private NotificationController notificationController;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testReadAllNotifications() throws Exception {
        assertEquals("readAllNotifications() did not return http status code 200 (ok)", HttpStatus.OK, notificationController.readAllNotifications().getStatusCode());
    }

    @Test
    public void testCreateNotification() throws Exception {
        assertEquals("createNotification() did not return http status code 201 (created)", HttpStatus.CREATED, notificationController.createNotification(new NotificationModel()).getStatusCode());
        assertEquals("createNotification() did not return http status code 204 (no content)", HttpStatus.NO_CONTENT, notificationController.createNotification(null).getStatusCode());
    }

    @Test
    public void testReadPersonalNotifications() throws Exception {
        assertEquals("readPersonalNotifications() did not return http status code 200 (ok)", HttpStatus.OK, notificationController.readPersonalNotifications(new Random().nextLong()).getStatusCode());
    }

}
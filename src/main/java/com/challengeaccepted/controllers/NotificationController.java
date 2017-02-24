package com.challengeaccepted.controllers;

import com.challengeaccepted.models.Notification;
import com.challengeaccepted.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
public class NotificationController {

    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @CrossOrigin
    @RequestMapping(value = "/notifications/", method = RequestMethod.GET)
    public ResponseEntity<List<Notification>> readAllNotifications() {
        return new ResponseEntity<>(notificationService.getAllNotificationsFromDatabase(), HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/notification/", method = RequestMethod.POST)
    public ResponseEntity createNotification(@RequestBody Notification notification) {
        if (notification != null) {
            notificationService.saveNotificationToDatabase(notification);
            return new ResponseEntity(HttpStatus.CREATED);
        } else {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
    }

    @CrossOrigin
    @RequestMapping(value = "/user/{id}/notifications/", method = RequestMethod.GET)
    public ResponseEntity<List<Notification>> readPersonalNotifications(@PathVariable Long id){
        List<Notification> notifications;

        notifications = notificationService.getAllNotificationsFromChallengeCreator(id);
        notifications.addAll(notificationService.getAllNotificationsFromChallengeClaimer(id));
        notifications.addAll(notificationService.getAllNotificationsFromChallengeUpvotersId(id));
        notifications = removeDuplicateEntriesFromList(notifications);

        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }

    private List<Notification> removeDuplicateEntriesFromList(List<Notification> notifications){

        Set<Notification> notificationSet = new HashSet<>();
        notificationSet.addAll(notifications);
        notifications.clear();
        notifications.addAll(notificationSet);

        return notifications;
    }
}
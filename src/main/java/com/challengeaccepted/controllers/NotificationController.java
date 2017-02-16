package com.challengeaccepted.controllers;

import com.challengeaccepted.models.NotificationModel;
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
    public ResponseEntity<List<NotificationModel>> readAllNotifications() {
        return new ResponseEntity<>(notificationService.getAllNotificationsFromDatabase(), HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/notification/", method = RequestMethod.POST)
    public ResponseEntity createNotification(@RequestBody NotificationModel notificationModel) {
        if (notificationModel != null) {
            notificationService.saveNotificationToDatabase(notificationModel);
            return new ResponseEntity(HttpStatus.CREATED);
        } else {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
    }

    @CrossOrigin
    @RequestMapping(value = "/user/{id}/notifications/", method = RequestMethod.GET)
    public ResponseEntity<List<NotificationModel>> readPersonalNotifications(@PathVariable Long id){
        List<NotificationModel> notifications;

        notifications = notificationService.getAllNotificationsFromChallengeCreator(id);
        notifications.addAll(notificationService.getAllNotificationsFromChallengeClaimer(id));
        notifications.addAll(notificationService.getAllNotificationsFromChallengeUpvotersId(id));
        notifications = removeDuplicateEntriesFromList(notifications);

        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }

    private List<NotificationModel> removeDuplicateEntriesFromList(List<NotificationModel> notifications){

        Set<NotificationModel> notificationSet = new HashSet<>();
        notificationSet.addAll(notifications);
        notifications.clear();
        notifications.addAll(notificationSet);

        return notifications;
    }
}
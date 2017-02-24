package com.challengeaccepted.services;

import com.challengeaccepted.models.Notification;
import com.challengeaccepted.repositories.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    public void saveNotificationToDatabase(Notification notification) {
        notificationRepository.saveAndFlush(notification);
    }

    public List<Notification> getAllNotificationsFromDatabase() {
        return notificationRepository.findAll();
    }

    public List<Notification> getAllNotificationsFromChallengeCreator(Long id) {
        return notificationRepository.getByInteractedChallengeChallengeCreatorId(id);
    }

    public List<Notification> getAllNotificationsFromChallengeClaimer(Long id){
        return notificationRepository.getByInteractedChallengeChallengeClaimerId(id);
    }

    public List<Notification> getAllNotificationsFromChallengeUpvotersId(Long id) {
        return notificationRepository.getByInteractedChallengeChallengeUpvotersId(id);
    }

}
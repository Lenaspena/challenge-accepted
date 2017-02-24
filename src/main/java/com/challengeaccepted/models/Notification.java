package com.challengeaccepted.models;

import com.challengeaccepted.models.wrappers.NotificationInfo;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
public class Notification implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDateTime dateTimeOfNotification;
    @ManyToOne
    private User interactor;
    @ManyToOne
    private Challenge interactedChallenge;
    @Embedded
    private NotificationInfo notificationInfo;

    public Notification() {
    }

    public Notification(User interactor, Challenge interactedChallenge, NotificationInfo notificationInfo) {
        this.interactor = interactor;
        this.interactedChallenge = interactedChallenge;
        this.notificationInfo = notificationInfo;
    }

    public Long getId() {
        return id;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    public LocalDateTime getDateTimeOfNotification() {
        return dateTimeOfNotification;
    }

    public void setDateTimeOfNotification(LocalDateTime dateTimeOfNotification) {
        this.dateTimeOfNotification = dateTimeOfNotification;
    }

    public User getInteractor() {
        return interactor;
    }

    public void setInteractor(User interactor) {
        this.interactor = interactor;
    }

    public Challenge getInteractedChallenge() {
        return interactedChallenge;
    }

    public void setInteractedChallenge(Challenge interactedChallenge) {
        this.interactedChallenge = interactedChallenge;
    }

    public NotificationInfo getNotificationInfo() {
        return notificationInfo;
    }

    public void setNotificationInfo(NotificationInfo notificationInfo) {
        this.notificationInfo = notificationInfo;
    }
}
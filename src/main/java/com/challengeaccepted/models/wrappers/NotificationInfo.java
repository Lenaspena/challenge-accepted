package com.challengeaccepted.models.wrappers;

import com.challengeaccepted.models.enums.Action;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;

@Embeddable
public class NotificationInfo implements Serializable {

    @Enumerated(EnumType.STRING)
    private Action action;
    private String notificationInfo;

    public NotificationInfo(Action action, String notificationInfo) {
        this.action = action;
        this.notificationInfo = notificationInfo;
    }

    public NotificationInfo(Action action) {
        this.action = action;
    }

    public NotificationInfo() {
    }

    public String getNotificationInfo() {
        return notificationInfo;
    }

    public void setNotificationInfo(String notificationInfo) {
        this.notificationInfo = notificationInfo;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }
}

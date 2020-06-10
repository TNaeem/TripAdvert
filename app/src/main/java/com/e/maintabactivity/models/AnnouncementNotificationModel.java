package com.e.maintabactivity.models;

import java.io.Serializable;

public class AnnouncementNotificationModel implements Serializable {

    private int notificationId;
    private AnnouncementModel announcement;

    public AnnouncementNotificationModel() {
    }

    public AnnouncementNotificationModel(int notificationId, AnnouncementModel announcement) {
        this.notificationId = notificationId;
        this.announcement = announcement;
    }

    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public AnnouncementModel getAnnouncement() {
        return announcement;
    }

    public void setAnnouncement(AnnouncementModel announcement) {
        this.announcement = announcement;
    }
}

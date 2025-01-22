package com.edj.teamoop.service;

import com.edj.teamoop.model.Notification.Notification;
import com.edj.teamoop.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    @Autowired
    NotificationRepository notificationRepository;

    public List<Notification> getNotificationsByUserId(Long userId) {
        return notificationRepository.findByUserId(userId);
    }

    public void addNotification(Notification notification) {
        notificationRepository.save(notification);
    }

    public long getNumberOfUnreadNotification(Long userId) {
        return notificationRepository.countUnreadNotificationsWithLimit(userId);
    }

}

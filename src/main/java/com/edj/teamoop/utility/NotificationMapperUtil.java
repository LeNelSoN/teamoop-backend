package com.edj.teamoop.utility;

import com.edj.teamoop.dto.notification.NotificationDTO;
import com.edj.teamoop.mapper.NotificationMapper;
import com.edj.teamoop.model.Notification.MessageNotification;
import com.edj.teamoop.model.Notification.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotificationMapperUtil {
    private final NotificationMapper notificationMapper;

    @Autowired
    public NotificationMapperUtil(NotificationMapper notificationMapper) {
        this.notificationMapper = notificationMapper;
    }

    public NotificationDTO mapNotificationToDTO(Notification notification) {
        if (notification instanceof MessageNotification) {
            return notificationMapper.messageNotificationToMessageNotificationDTO((MessageNotification) notification);
        }
        return notificationMapper.toDTO(notification);
    }
}

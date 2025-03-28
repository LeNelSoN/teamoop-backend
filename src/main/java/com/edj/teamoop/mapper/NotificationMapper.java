package com.edj.teamoop.mapper;

import com.edj.teamoop.dto.notification.MessageNotificationDTO;
import com.edj.teamoop.dto.notification.NotificationDTO;
import com.edj.teamoop.model.Notification.MessageNotification;
import com.edj.teamoop.model.Notification.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface NotificationMapper {

    MessageNotificationDTO messageNotificationToMessageNotificationDTO(MessageNotification messageNotification);

    NotificationDTO toDTO(Notification notification);
}

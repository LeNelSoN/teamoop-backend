package com.edj.teamoop.dto.notification;

import com.edj.teamoop.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Data
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MessageNotificationDTO extends NotificationDTO{
    private String message;

    public MessageNotificationDTO(long id, User user, boolean isRead, LocalDate createdAt, String message) {
        super( id, user, isRead, createdAt);
        this.message = message;
    }
}

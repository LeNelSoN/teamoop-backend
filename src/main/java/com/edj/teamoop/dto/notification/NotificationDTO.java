package com.edj.teamoop.dto.notification;

import com.edj.teamoop.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDTO {
    private Long id;
    @JsonIgnore
    private User user;
    private boolean isRead;
    private LocalDate createdAt;
}

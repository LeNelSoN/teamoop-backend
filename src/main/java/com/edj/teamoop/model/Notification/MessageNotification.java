package com.edj.teamoop.model.Notification;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class MessageNotification extends Notification{
    @Column(name = "message", nullable = false)
    private String message;
}

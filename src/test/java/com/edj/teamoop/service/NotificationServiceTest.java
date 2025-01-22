package com.edj.teamoop.service;

import com.edj.teamoop.model.Notification.MessageNotification;
import com.edj.teamoop.model.Notification.Notification;
import com.edj.teamoop.repository.NotificationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NotificationServiceTest {
    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private NotificationService notificationService;

    @Test
    void testGetNotificationsByUserId() {

        Long userId = 1L;
        Notification notification1 = new MessageNotification();
        notification1.setId(1L);
        notification1.setUserId(userId);
        Notification notification2 = new MessageNotification();
        notification2.setId(2L);
        notification2.setUserId(userId);

        when(notificationRepository.findByUserId(userId)).thenReturn(Arrays.asList(notification1, notification2));

        List<Notification> notifications = notificationService.getNotificationsByUserId(userId);

        assertThat(notifications).hasSize(2);
        assertThat(notifications.get(0).getId()).isEqualTo(1L);
        assertThat(notifications.get(1).getId()).isEqualTo(2L);

        verify(notificationRepository, times(1)).findByUserId(userId);
    }
        @Test
    void testAddNotification() {

        Notification notification = new MessageNotification();
        notification.setId(1L);
        notification.setUserId(1L);

        when(notificationRepository.save(notification)).thenReturn(notification);

        notificationService.addNotification(notification);

        verify(notificationRepository, times(1)).save(notification);
    }

    @Test
    void testGetNumberOfUnreadNotification() {

        Long userId = 1L;
        long unreadCount = 5L;

        when(notificationRepository.countUnreadNotificationsWithLimit(userId)).thenReturn(unreadCount);

        long result = notificationService.getNumberOfUnreadNotification(userId);

        assertThat(result).isEqualTo(unreadCount);
        verify(notificationRepository, times(1)).countUnreadNotificationsWithLimit(userId);
    }
}

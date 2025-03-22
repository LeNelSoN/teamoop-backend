package com.edj.teamoop.service;

import com.edj.teamoop.dto.notification.MessageNotificationDTO;
import com.edj.teamoop.dto.notification.NotificationDTO;
import com.edj.teamoop.model.Notification.MessageNotification;
import com.edj.teamoop.model.Notification.Notification;
import com.edj.teamoop.model.User;
import com.edj.teamoop.repository.NotificationRepository;
import com.edj.teamoop.utility.NotificationMapperUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NotificationServiceTest {
    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private NotificationMapperUtil notificationMapperUtil;

    @InjectMocks
    private NotificationService notificationService;

    @Test
    void testGetNotificationsByUserId() {
        Long userId = 1L;

        User user = new User();
        user.setId(userId);
        user.setName("jean neige");

        Notification notification1 = new MessageNotification();
        notification1.setId(1L);
        notification1.setUser(user);
        Notification notification2 = new MessageNotification();
        notification2.setId(2L);
        notification2.setUser(user);

        PageRequest pageable = PageRequest.of(0, 10);
        Page<Notification> notificationsPage = new PageImpl<>(Arrays.asList(notification1, notification2), pageable, 2);

        when(notificationRepository.findByUser_UserName("jean neige", pageable)).thenReturn(notificationsPage);

        NotificationDTO notificationDTO1 = new MessageNotificationDTO(1L, user, true, LocalDate.now(), "Test message 1");
        NotificationDTO notificationDTO2 = new MessageNotificationDTO(2L, user, false, LocalDate.now(), "Test message 2");

        when(notificationMapperUtil.mapNotificationToDTO(notification1)).thenReturn(notificationDTO1);
        when(notificationMapperUtil.mapNotificationToDTO(notification2)).thenReturn(notificationDTO2);

        Page<NotificationDTO> notificationDTOPage = notificationService.getNotificationsByUserName("jean neige", 0, 10);

        assertThat(notificationDTOPage).hasSize(2);
        assertThat(notificationDTOPage.getContent().get(0).getId()).isEqualTo(1L);
        assertThat(notificationDTOPage.getContent().get(1).getId()).isEqualTo(2L);

        verify(notificationRepository, times(1)).findByUser_UserName("jean neige", pageable);
    }
        @Test
    void testAddNotification() {
        User user = new User();
        user.setId(1L);

        Notification notification = new MessageNotification();
        notification.setId(1L);
        notification.setUser(user);

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

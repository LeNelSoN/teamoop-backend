package com.edj.teamoop.service;

import com.edj.teamoop.dto.notification.NotificationDTO;
import com.edj.teamoop.model.Notification.Notification;
import com.edj.teamoop.repository.NotificationRepository;
import com.edj.teamoop.utility.NotificationMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationMapperUtil notificationMapperUtil;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository, NotificationMapperUtil notificationMapperUtil) {
        this.notificationRepository = notificationRepository;
        this.notificationMapperUtil = notificationMapperUtil;
    }

    public Page<NotificationDTO> getNotificationsByUserName(Long userId, int page, int size) {
        if (page < 0 || size <= 0) {
            throw new IllegalArgumentException("Invalid pagination parameters: Page index must be >= 0 and size must be > 0");
        }
        int maxSize = 50;
        PageRequest pageable = PageRequest.of(page, Math.min(size, maxSize));

        Page<Notification> notificationsPage = notificationRepository.findByUserId(userId, pageable);

        List<NotificationDTO> notificationDTOList = notificationsPage.getContent().stream()
                .map(notificationMapperUtil::mapNotificationToDTO)
                .collect(Collectors.toList());

        return new PageImpl<>(notificationDTOList, pageable, notificationsPage.getTotalElements());

    }

    public void addNotification(Notification notification) {
        notificationRepository.save(notification);
    }

    public long getNumberOfUnreadNotification(Long userId) {
        return notificationRepository.countUnreadNotificationsWithLimit(userId);
    }

}

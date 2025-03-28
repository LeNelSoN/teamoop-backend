package com.edj.teamoop.service.cache;

import com.edj.teamoop.dto.notification.NotificationDTO;
import com.edj.teamoop.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class NotificationCacheService {
    private final NotificationService notificationService;

    @Autowired
    public NotificationCacheService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @Cacheable(value = "notifications", key = "'page:' + #page + '-size:' + #size")
    public Page<NotificationDTO> getCachedPage(Long userId, int page, int size) {
        return notificationService.getNotificationsByUserName(userId, page, size);
    }
}

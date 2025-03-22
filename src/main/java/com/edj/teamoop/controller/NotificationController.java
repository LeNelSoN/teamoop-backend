package com.edj.teamoop.controller;

import com.edj.teamoop.dto.notification.NotificationDTO;
import com.edj.teamoop.service.cache.NotificationCacheService;
import com.edj.teamoop.utility.SecurityContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationCacheService notificationCacheService;

    @Autowired
    public NotificationController(NotificationCacheService notificationCacheService) {
        this.notificationCacheService = notificationCacheService;
    }

    @GetMapping
    public ResponseEntity<Page<NotificationDTO>> getUserNotification(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        String userPrincipalName = SecurityContextUtil.getUserPrincipalName();
        Page<NotificationDTO> notificationDTOPage = notificationCacheService.getCachedPage(userPrincipalName, page, size);
        if(notificationDTOPage.isEmpty()){
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(notificationDTOPage);
        }
    }
}

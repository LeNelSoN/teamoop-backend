package com.edj.teamoop.controller;

import com.edj.teamoop.dto.notification.NotificationDTO;
import com.edj.teamoop.service.JwtService;
import com.edj.teamoop.service.cache.NotificationCacheService;
import com.edj.teamoop.utility.SecurityContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationCacheService notificationCacheService;
    private final JwtService jwtService;

    @Autowired
    public NotificationController(NotificationCacheService notificationCacheService, JwtService jwtService) {
        this.notificationCacheService = notificationCacheService;
        this.jwtService = jwtService;
    }

    @GetMapping
    public ResponseEntity<Page<NotificationDTO>> getUserNotification(@RequestHeader("Authorization") String bearer, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        Long userId = jwtService.extractUserIdFromToken(bearer.replace("Bearer ", ""));
        Page<NotificationDTO> notificationDTOPage = notificationCacheService.getCachedPage(userId, page, size);
        if(notificationDTOPage.isEmpty()){
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(notificationDTOPage);
        }
    }
}

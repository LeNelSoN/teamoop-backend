package com.edj.teamoop.repository;

import com.edj.teamoop.model.Notification.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    Page<Notification> findByUser_UserName(String userName, Pageable pageable);

    @Query(value = "SELECT COUNT(*) FROM (SELECT id FROM notification WHERE user_id = :userId AND is_read = false LIMIT 99) AS limited_count", nativeQuery = true)
    long countUnreadNotificationsWithLimit(@Param("userId") Long userId);
}

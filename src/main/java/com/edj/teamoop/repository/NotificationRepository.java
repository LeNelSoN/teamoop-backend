package com.edj.teamoop.repository;

import com.edj.teamoop.model.Notification.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserId(Long userId);

    @Query(value = "SELECT COUNT(*) FROM (SELECT id FROM notification WHERE user_id = :userId AND is_read = false LIMIT 99) AS limited_count", nativeQuery = true)
    long countUnreadNotificationsWithLimit(Long userId);
}

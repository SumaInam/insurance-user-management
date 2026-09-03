package com.insurance.service;

import com.insurance.dto.NotificationRequest;
import com.insurance.entity.Notification;

import java.util.List;

public interface NotificationService {

    Notification createNotification(NotificationRequest request);

    List<Notification> getUserNotifications(Long userId);

    void markAllAsRead(Long userId);
}
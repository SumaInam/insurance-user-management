package com.insurance.controller;

import com.insurance.dto.NotificationRequest;
import com.insurance.entity.Notification;
import com.insurance.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping
    public Notification createNotification(@RequestBody NotificationRequest request){

        return notificationService.createNotification(request);
    }

    @GetMapping("/{userId}")
    public List<Notification> getNotifications(@PathVariable Long userId){

        return notificationService.getUserNotifications(userId);
    }

    @PutMapping("/read-all/{userId}")
    public String markAllRead(@PathVariable Long userId){

        notificationService.markAllAsRead(userId);

        return "All Notifications Read";
    }
}
package com.insurance.service.impl;

import com.insurance.dto.NotificationRequest;
import com.insurance.entity.Notification;
import com.insurance.entity.User;
import com.insurance.repository.NotificationRepository;
import com.insurance.repository.UserRepository;
import com.insurance.service.EmailService;
import com.insurance.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    private final UserRepository userRepository;

    private final EmailServiceImpl emailService;

    @Override
    public Notification createNotification(NotificationRequest request) {

        User user = userRepository.findById(
                                request.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));

        Notification notification = new Notification();

        notification.setTitle(request.getTitle());

        notification.setMessage(request.getMessage());

        notification.setNotificationType(request.getNotificationType());

        notification.setCreatedDate(LocalDateTime.now());

        notification.setStatus("UNREAD");

        notification.setUser(user);

        Notification saved = notificationRepository.save(notification);

        try {

            emailService.sendEmail(

                    user.getEmail(),

                    request.getTitle(),

                    request.getMessage()

            );

        } catch (Exception e){

            System.out.println("Email sending failed");
        }

        return saved;
    }

    @Override
    public List<Notification> getUserNotifications(Long userId) {

        return notificationRepository.findByUserUserId(userId);
    }

    @Override
    public void markAllAsRead(Long userId) {

        List<Notification> notifications = notificationRepository.findByUserUserId(userId);

        notifications.forEach(n -> n.setStatus("READ"));

        notificationRepository.saveAll(notifications);
    }
}

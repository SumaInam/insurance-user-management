package com.insurance.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "notification")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationId;

    private String title;

    private String message;

    private String notificationType;

    private LocalDateTime createdDate;

    private String status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
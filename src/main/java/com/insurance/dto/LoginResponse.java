package com.insurance.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {

    private Long userId;

    private String userType;

    private String token;

    private LocalDateTime tokenStartTime;

    private LocalDateTime tokenEndTime;
}
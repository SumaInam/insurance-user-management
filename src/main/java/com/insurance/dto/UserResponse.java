package com.insurance.dto;


import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class UserResponse {

    private Long id;

    private Long userId;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private LocalDate dateOfBirth;

    private String gender;

    private String status;

    private LocalDateTime createDate;

    private LocalDateTime updateDate;

    private String userTypeName;
}
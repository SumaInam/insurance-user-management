package com.insurance.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserRequest {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Email
    private String email;

    @Size(min=5)
    private String password;

    @Pattern(regexp="^[0-9]{10}$")
    private String phoneNumber;

    private LocalDate dateOfBirth;

    private String gender;

    private String status;

    @NotNull(message = "User Type is required")
    private Long userTypeId;
}

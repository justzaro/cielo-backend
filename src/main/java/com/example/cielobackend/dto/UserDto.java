package com.example.cielobackend.dto;

import com.example.cielobackend.common.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserDto {
    @NotBlank(message = "First name field should not be blank")
    @Size(max = 255)
    private String firstName;

    @NotBlank(message = "First name field should not be blank")
    @Size(max = 255)
    private String lastName;

    @NotBlank(message = "Last name field should not be blank")
    @Size(max = 255)
    private String username;

    @NotBlank(message = "Password field should not be blank")
    @Size(max = 255)
    private String password;

    @NotBlank(message = "Email field should not be blank")
    @Email(message = "Enter a valid email address")
    @Size(max = 255)
    private String email;

    @Size(max = 255)
    private String address;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dateOfBirth;

    private Double balance = 0.0;

    private Double bonusPoints = 0.0;

    private Boolean isLocked = false;

    private Boolean isEnabled = true;
}

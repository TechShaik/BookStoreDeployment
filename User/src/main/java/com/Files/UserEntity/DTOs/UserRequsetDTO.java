package com.Files.UserEntity.DTOs;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequsetDTO {
	@Column(unique = true, nullable = false)
    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "First name is required")
    private String f_name;

    @NotBlank(message = "Last name is required")
    private String l_name;

    @NotNull(message = "Date of birth is required")
    private LocalDate user_dob;

    @NotBlank(message = "Password is required")
    @Pattern(
        regexp = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$",
        message = "Password must contain at least one uppercase letter, one digit, one special character, and be at least 8 characters long without spaces.")
    private String user_password;
    
	@Column(unique = true, nullable = false)
    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is required")
    private String user_email;

    @NotBlank(message = "User role is required")
    private String user_role;

}


package com.lioneats.lioneats_backend.dto;

import java.util.List;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name must be less than 100 characters")
    private String name;

    @NotBlank(message = "Username is required")
    @Size(max = 50, message = "Username must be less than 50 characters")
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotNull(message = "Age must be provided")
    @Min(value = 0, message = "Age must be a positive number")
    @Max(value = 150, message = "Age must be less than or equal to 150")
    private Integer age;

    @NotBlank(message = "Gender is required")
    private String gender;

    @Size(max = 100, message = "Country must be less than 100 characters")
    private String country;

    @NotNull(message = "Preferred budget is required")
    private String preferredBudget;

    private boolean likesSpicy;

    private List<String> allergies;
    private List<String> dishPreferences;
}


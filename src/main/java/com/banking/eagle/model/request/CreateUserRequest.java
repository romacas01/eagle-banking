package com.banking.eagle.model.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateUserRequest {
    @NotBlank(message = "Username is required")
    public String username;

    @NotBlank(message = "Password is required")
    public String password;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    public String email;

    @NotBlank(message = "Full name is required")
    public String fullName;
}

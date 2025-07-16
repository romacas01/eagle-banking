package com.banking.eagle.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateUserRequest {
    @NotBlank(message = "Email is required")
    public String email;

    @NotBlank(message = "Full name is required")
    public String fullName;
    //don't provide field for username as we don't want user to change it
}

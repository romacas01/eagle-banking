package com.banking.eagle.model.request;

import lombok.Data;

@Data
public class UpdateUserRequest {
    public String email;
    public String fullName;
    //don't provide field for username as we don't want user to change it
}

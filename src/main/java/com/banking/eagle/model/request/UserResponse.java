package com.banking.eagle.model.request;

import com.banking.eagle.model.User;
import lombok.Data;

@Data
public class UserResponse {
    public Long id;
    public String username;
    public String email;
    public String fullName;

    public UserResponse(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.fullName = user.getFullName();
    }
}

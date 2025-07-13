package com.banking.eagle.model.request;

import lombok.Data;

@Data
public class AuthResponse {
    private String token;
    public AuthResponse(String token) { this.token = token; }
}

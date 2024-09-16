package com.nikhil.trading.response;

import lombok.Data;

@Data
public class AuthResponse {
    private String jwtToken;
    private boolean status;
    private String message;
    private boolean IsTwoFactorAuthEnable;
    private String session;
}

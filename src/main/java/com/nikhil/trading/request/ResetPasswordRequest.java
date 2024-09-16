package com.nikhil.trading.request;

import lombok.Data;

@Data
public class ResetPasswordRequest {
    String otp;
    String password;
}

package com.nikhil.trading.service;

import com.nikhil.trading.enums.VerificationType;
import com.nikhil.trading.modal.ForgotPasswordToken;
import com.nikhil.trading.modal.User;

public interface ForgetPasswordService {
    ForgotPasswordToken createToken(User user, String id, String otp, VerificationType verificationType, String sendTo);
    ForgotPasswordToken findById(String id);
    ForgotPasswordToken findByUserId(Long userId);
    void deleteForgetPassword(ForgotPasswordToken forgetPassword);
    void saveToken(ForgotPasswordToken token);

}

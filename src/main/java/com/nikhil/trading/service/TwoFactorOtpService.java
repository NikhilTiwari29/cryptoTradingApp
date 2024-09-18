package com.nikhil.trading.service;

import com.nikhil.trading.modal.TwoFactorOtp;
import com.nikhil.trading.modal.User;

public interface TwoFactorOtpService {
    TwoFactorOtp createTwofactorOtp(User user, String otp, String jwtToken);
    TwoFactorOtp findByUser(Long userId);
    TwoFactorOtp findById(String id);
    boolean verifyTwoFactorOtp(TwoFactorOtp twoFactorOtp,String otp);
    void deleteTwoFactorOtp(TwoFactorOtp twoFactorOtp);
}

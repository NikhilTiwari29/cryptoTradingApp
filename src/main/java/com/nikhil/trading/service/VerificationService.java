package com.nikhil.trading.service;

import com.nikhil.trading.enums.VerificationType;
import com.nikhil.trading.modal.User;
import com.nikhil.trading.modal.VerificationCode;

public interface VerificationService {
    VerificationCode sendVerificationOTP(User user, VerificationType verificationType);

    VerificationCode findVerificationById(Long id) throws Exception;

    VerificationCode findUsersVerification(Long userId) throws Exception;

    Boolean verifyOtp(String opt, VerificationCode verificationCode);

    void deleteVerificationCode(VerificationCode verificationCode);

}
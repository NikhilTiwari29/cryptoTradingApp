package com.nikhil.trading.service;

import com.nikhil.trading.enums.VerificationType;
import com.nikhil.trading.modal.ForgotPasswordToken;
import com.nikhil.trading.modal.User;
import com.nikhil.trading.repository.ForgetPasswordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ForgetPasswordServiceImpl implements ForgetPasswordService {
    @Autowired
    private ForgetPasswordRepository forgetPasswordRepository;


    @Override
    public ForgotPasswordToken createToken(User user, String id, String otp, VerificationType verificationType, String sendTo) {
        ForgotPasswordToken forgetPassword = new ForgotPasswordToken();
        forgetPassword.setUser(user);
        forgetPassword.setSendTo(sendTo);
        forgetPassword.setOtp(otp);
        forgetPassword.setId(id);
        forgetPassword.setVerificationType(verificationType);
        return forgetPasswordRepository.save(forgetPassword);
    }

    @Override
    public ForgotPasswordToken findById(String id) {
        return forgetPasswordRepository.findById(id).orElse(null);
    }

    @Override
    public ForgotPasswordToken findByUserId(Long userId) {
        return forgetPasswordRepository.findByUserId(userId);
    }

    @Override
    public void deleteForgetPassword(ForgotPasswordToken forgetPassword) {
        forgetPasswordRepository.delete(forgetPassword);
    }

    @Override
    public void saveToken(ForgotPasswordToken token) {
        forgetPasswordRepository.save(token);
    }

}

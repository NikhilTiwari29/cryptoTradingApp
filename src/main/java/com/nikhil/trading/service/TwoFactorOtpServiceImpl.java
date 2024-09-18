package com.nikhil.trading.service;

import com.nikhil.trading.modal.TwoFactorOtp;
import com.nikhil.trading.modal.User;
import com.nikhil.trading.repository.TwoFactorOtpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class TwoFactorOtpServiceImpl implements TwoFactorOtpService {

    @Autowired
    private TwoFactorOtpRepository twoFactorOtpRepository;

    // Inject OTP expiration time from properties
    @Value("${otp.expiration.minutes}")
    private int otpExpirationMinutes;

    @Override
    public TwoFactorOtp createTwofactorOtp(User user, String otp, String jwtToken) {
        String id = UUID.randomUUID().toString();
        TwoFactorOtp twoFactorOtp = new TwoFactorOtp();
        twoFactorOtp.setOtp(otp);
        twoFactorOtp.setUser(user);
        twoFactorOtp.setId(id);
        twoFactorOtp.setJwtToken(jwtToken);
        twoFactorOtp.setCreatedAt(LocalDateTime.now());
        return twoFactorOtpRepository.save(twoFactorOtp);
    }

    @Override
    public TwoFactorOtp findByUser(Long userId) {
        return twoFactorOtpRepository.findByUserId(userId);
    }

    @Override
    public TwoFactorOtp findById(String id) {
        return twoFactorOtpRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("OTP not found for the given ID")); // Throwing an exception if not found
    }

    public boolean verifyTwoFactorOtp(TwoFactorOtp twoFactorOtp, String otp) {
        if (twoFactorOtp.getCreatedAt().isBefore(LocalDateTime.now().minusMinutes(otpExpirationMinutes))) {
            throw new IllegalArgumentException("OTP has expired");
        }
        return twoFactorOtp.getOtp().equals(otp);
    }


    @Override
    public void deleteTwoFactorOtp(TwoFactorOtp twoFactorOtp) {
        twoFactorOtpRepository.delete(twoFactorOtp);
    }
}

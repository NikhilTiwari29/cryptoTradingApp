package com.nikhil.trading.service;

import com.nikhil.trading.enums.VerificationType;
import com.nikhil.trading.modal.User;
import com.nikhil.trading.modal.VerificationCode;
import com.nikhil.trading.repository.VerificationCodeRepository;
import com.nikhil.trading.utils.OtpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VerificationServiceImpl implements VerificationService {

    @Autowired
    private VerificationCodeRepository verificationCodeRepository;

    @Override
    @Transactional
    public VerificationCode sendVerificationOTP(User user, VerificationType verificationType) {
        VerificationCode verificationCode = new VerificationCode();
        verificationCode.setOtp(OtpUtils.generateOtp());
        verificationCode.setVerificationType(verificationType);
        verificationCode.setUser(user);
        return verificationCodeRepository.save(verificationCode);
    }

    @Override
    public VerificationCode findVerificationById(Long userId) throws  Exception {
        return verificationCodeRepository.findById(userId)
                .orElseThrow(() -> new Exception("Verification code not found with userId: " + userId));
    }

    @Override
    public VerificationCode findUsersVerification(Long userId) throws Exception {
        return verificationCodeRepository.findByUserId(userId);
    }

    @Override
    public Boolean verifyOtp(String opt, VerificationCode verificationCode) {
        return verificationCode != null && opt.equals(verificationCode.getOtp());
    }


    @Override
    @Transactional
    public void deleteVerificationCode(VerificationCode verificationCode) {
        verificationCodeRepository.delete(verificationCode);
    }
}

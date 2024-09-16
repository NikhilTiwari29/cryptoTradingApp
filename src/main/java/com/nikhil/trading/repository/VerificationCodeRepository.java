package com.nikhil.trading.repository;

import com.nikhil.trading.modal.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationCodeRepository extends JpaRepository<VerificationCode,Long> {
    VerificationCode findByUserId(Long id);
}

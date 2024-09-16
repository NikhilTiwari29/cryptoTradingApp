package com.nikhil.trading.modal;

import com.nikhil.trading.enums.VerificationType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class ForgotPasswordToken {
    @Id
    private String id;
    @OneToOne
    private User user;
    private String otp;
    private VerificationType verificationType;
    private String sendTo;
}

package com.nikhil.trading.modal;

import com.nikhil.trading.enums.VerificationType;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class TwoFactorAuth {
    private boolean isEnabled = false;
    private VerificationType verificationType;

}

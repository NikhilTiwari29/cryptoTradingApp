package com.nikhil.trading.model;

import com.nikhil.trading.enums.VerificationType;
import lombok.Data;

@Data
public class TwoFactorAuth {

    private boolean isEnabled = false;
    private VerificationType sendTo;
}

package com.nikhil.trading.request;

import com.nikhil.trading.enums.VerificationType;
import lombok.Data;

@Data
public class UpdatePasswordRequest {
    private String sendTo;
    private VerificationType verificationType;
}

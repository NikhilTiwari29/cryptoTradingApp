package com.nikhil.trading.modal;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class TwoFactorOtp {

    @Id
    private String id; // UUID or another unique identifier

    private String otp; // OTP value

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // Prevent exposure in API responses
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false) // Define fetch and cascade strategy
    @JoinColumn(name = "user_id", referencedColumnName = "id") // Explicit FK definition
    private User user;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // Hide JWT in responses
    private String jwtToken;

    // Field to store the creation time of the OTP
    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime expiresAt;
}

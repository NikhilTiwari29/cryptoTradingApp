package com.nikhil.trading.modal;

import com.nikhil.trading.enums.WithdrawalStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
public class Withdrawal {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private WithdrawalStatus withdrawalStatus;
    private BigDecimal amount;
    @ManyToOne
    private User user;
    private LocalDateTime createdAt = LocalDateTime.now();
}

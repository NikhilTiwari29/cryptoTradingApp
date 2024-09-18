package com.nikhil.trading.modal;

import com.nikhil.trading.enums.WalletTransactionType;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
public class WalletTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Wallet wallet;

    private WalletTransactionType walletTransactionType;

    private LocalDate date;

    private String transferId;

    private String purpose;

    private BigDecimal amount;

    WalletTransactionType type;


}
package com.nikhil.trading.service;


import com.nikhil.trading.enums.WalletTransactionType;
import com.nikhil.trading.modal.Wallet;
import com.nikhil.trading.modal.WalletTransaction;

import java.math.BigDecimal;
import java.util.List;

public interface WalletTransactionService {
    void createTransaction(Wallet wallet,
                                        WalletTransactionType type,
                                        String transferId,
                                        String purpose,
                                        BigDecimal amount
    );

    List<WalletTransaction> getTransactions(Wallet wallet, WalletTransactionType type);

}

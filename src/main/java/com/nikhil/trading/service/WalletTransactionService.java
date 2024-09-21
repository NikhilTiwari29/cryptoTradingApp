package com.nikhil.trading.service;

import com.nikhil.trading.enums.WalletTransactionType;
import com.nikhil.trading.model.Wallet;
import com.nikhil.trading.model.WalletTransaction;

import java.util.List;

public interface WalletTransactionService {
    WalletTransaction createTransaction(Wallet wallet,
                                        WalletTransactionType type,
                                        String transferId,
                                        String purpose,
                                        Long amount
    );

    List<WalletTransaction> getTransactions(Wallet wallet, WalletTransactionType type);

}

package com.nikhil.trading.service;

import com.nikhil.trading.modal.Order;
import com.nikhil.trading.modal.User;
import com.nikhil.trading.modal.Wallet;

import java.math.BigDecimal;

public interface WalletService {

    Wallet getUserWallet(User user);
    Wallet addBalanceToWallet(Wallet wallet, BigDecimal amount);
    Wallet findWalletById(Long id) throws Exception;
    Wallet walletToWalletTransfer(User sender,Wallet receiverWallet,BigDecimal amount) throws Exception;
    Wallet handleBuySellOrder(Order order, User user) throws Exception;
}

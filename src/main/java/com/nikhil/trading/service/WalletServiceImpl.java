package com.nikhil.trading.service;

import com.nikhil.trading.enums.OrderType;
import com.nikhil.trading.modal.Order;
import com.nikhil.trading.modal.User;
import com.nikhil.trading.modal.Wallet;
import com.nikhil.trading.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class WalletServiceImpl implements WalletService {

    @Autowired
    private WalletRepository walletRepository;

    @Override
    public Wallet getUserWallet(User user) {
        Wallet wallet = walletRepository.findByUserId(user.getId());
        if (wallet == null){
            Wallet newWallet = new Wallet();
            newWallet.setUser(user);
        }
        return wallet;
    }

    @Override
    public Wallet addBalanceToWallet(Wallet wallet, BigDecimal amount) {
        BigDecimal balance = wallet.getBalance();
        BigDecimal newBalance = balance.add(amount);
        wallet.setBalance(newBalance);
        return walletRepository.save(wallet);
    }

    @Override
    public Wallet findWalletById(Long id) throws Exception {
        Optional<Wallet> wallet = walletRepository.findById(id);
        if (wallet.isEmpty()) throw new Exception("wallet not found");
        return wallet.get();
    }

    @Override
    public Wallet walletToWalletTransfer(User sender, Wallet receiverWallet, BigDecimal amount) throws Exception {
        Wallet senderWallet = getUserWallet(sender);
        if (senderWallet.getBalance().compareTo(amount) < 0) {
            throw new Exception("Insufficient balance");
        }
        BigDecimal senderRemainingBalance = senderWallet.getBalance().subtract(amount);
        senderWallet.setBalance(senderRemainingBalance);
        walletRepository.save(senderWallet);

        BigDecimal addInReceiverWallet = receiverWallet.getBalance().add(amount);
        receiverWallet.setBalance(addInReceiverWallet);
        walletRepository.save(receiverWallet);
        return senderWallet;
    }

    @Override
    public Wallet handleBuySellOrder(Order order, User user) throws Exception {
        Wallet userWallet = getUserWallet(user);

        // Handle BUY order type
        if (order.getOrderType().equals(OrderType.BUY)) {
            // Check if the wallet has enough funds before performing the transaction
            if (userWallet.getBalance().compareTo(order.getPrice()) < 0) {
                throw new Exception("Insufficient funds for this transaction.");
            }

            // Deduct the order price from the wallet balance
            BigDecimal newBalance = userWallet.getBalance().subtract(order.getPrice());
            userWallet.setBalance(newBalance);
        }
        // Handle SELL order type
        else if (order.getOrderType().equals(OrderType.SELL)) {
            // Add the order price to the wallet balance
            BigDecimal newBalance = userWallet.getBalance().add(order.getPrice());
            userWallet.setBalance(newBalance);
        }
        else {
            throw new Exception("Invalid order type.");
        }

        return userWallet;
    }

}

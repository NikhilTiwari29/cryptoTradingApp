package com.nikhil.trading.repository;

import com.nikhil.trading.modal.Wallet;
import com.nikhil.trading.modal.WalletTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WalletTransactionRepository extends JpaRepository<WalletTransaction,Long> {

    List<WalletTransaction> findByWalletOrderByDateDesc(Wallet wallet);

}

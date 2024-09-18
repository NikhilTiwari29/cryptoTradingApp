package com.nikhil.trading.service;

import com.nikhil.trading.modal.User;
import com.nikhil.trading.modal.Withdrawal;

import java.math.BigDecimal;
import java.util.List;

public interface WithdrawalService {
    Withdrawal requestWithdrawal(BigDecimal amount, User user);
    Withdrawal proccedWithdrawal(Long withdrawalId,boolean accept) throws Exception;
    List<Withdrawal> getUserWithdrawalHistory(User user);
    List<Withdrawal> getAllWithdrawalRequest();
}

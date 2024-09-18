package com.nikhil.trading.service;

import com.nikhil.trading.enums.WithdrawalStatus;
import com.nikhil.trading.modal.User;
import com.nikhil.trading.modal.Withdrawal;
import com.nikhil.trading.repository.WithdrawalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class WithdrawalServiceImpl implements WithdrawalService{

    @Autowired
    private WithdrawalRepository withdrawalRepository;

    @Override
    public Withdrawal requestWithdrawal(BigDecimal amount, User user) {
        Withdrawal withdrawal = new Withdrawal();
        withdrawal.setAmount(amount);
        withdrawal.setUser(user);
        withdrawal.setWithdrawalStatus(WithdrawalStatus.PENDING);
        return withdrawalRepository.save(withdrawal);
    }

    @Override
    public Withdrawal proccedWithdrawal(Long withdrawalId, boolean accept) throws Exception {
        Optional<Withdrawal> withdrawal = withdrawalRepository.findById(withdrawalId);
        if (withdrawal.isEmpty()) throw new Exception("withdrawal not found");
        Withdrawal withdrawal1 = withdrawal.get();
        withdrawal1.setCreatedAt(LocalDateTime.now());
        if (accept){
            withdrawal1.setWithdrawalStatus(WithdrawalStatus.SUCCESS);
        }
        else {
            withdrawal1.setWithdrawalStatus(WithdrawalStatus.DECLINED);
        }
        return withdrawalRepository.save(withdrawal1);
    }

    @Override
    public List<Withdrawal> getUserWithdrawalHistory(User user) {
        return withdrawalRepository.findByUserId(user.getId());
    }

    @Override
    public List<Withdrawal> getAllWithdrawalRequest() {
        return withdrawalRepository.findAll();
    }
}

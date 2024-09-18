package com.nikhil.trading.controller;

import com.nikhil.trading.modal.Order;
import com.nikhil.trading.modal.User;
import com.nikhil.trading.modal.Wallet;
import com.nikhil.trading.modal.WalletTransaction;
import com.nikhil.trading.service.OrderService;
import com.nikhil.trading.service.UserService;
import com.nikhil.trading.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wallet")
public class WalletController {

    @Autowired
    private WalletService walletService;
    
    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @GetMapping("/")
    public ResponseEntity<Wallet> getUserWallet(@RequestHeader("Authorization") String jwtToken){
        User user = userService.findUserByJwt(jwtToken);
        Wallet userWallet = walletService.getUserWallet(user);
        return new ResponseEntity<>(userWallet, HttpStatus.OK);
    }

    @PutMapping("/{walletId}/transfer")
    public ResponseEntity<Wallet> walletToWalletTransfer(@RequestHeader("Authorization") String jwtToken,
                                                         @PathVariable Long walletId,
                                                         @RequestBody WalletTransaction walletTransaction) throws Exception {
        User senderUser = userService.findUserByJwt(jwtToken);
        Wallet receiverWallet = walletService.findWalletById(walletId);
        Wallet wallet = walletService.walletToWalletTransfer(senderUser, receiverWallet, walletTransaction.getAmount());
        return new ResponseEntity<>(wallet, HttpStatus.ACCEPTED);
    }

    @PutMapping("/order/{orderId}/pay")
    public ResponseEntity<Wallet> handleBuySellOrder(@RequestHeader("Authorization") String jwtToken,
                                                         @PathVariable Long orderId) throws Exception {
        User senderUser = userService.findUserByJwt(jwtToken);
        Order order = orderService.getOrderById(orderId);
        Wallet wallet = walletService.handleBuySellOrder(order, senderUser);
        return new ResponseEntity<>(wallet, HttpStatus.ACCEPTED);
    }
}

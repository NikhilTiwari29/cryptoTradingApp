package com.nikhil.trading.service;

import com.nikhil.trading.enums.VerificationType;
import com.nikhil.trading.modal.User;


public interface UserService {
    User findUserByJwt(String JwtToken);
    User findUserByEmail(String email);
    User findUserById(Long id);
    User enableTwoFactorAuthentication(VerificationType verificationType, String sendTo, User user);
    void updatePassword(User user , String newPassword);
    public User verifyUser(User user) throws Exception;
}

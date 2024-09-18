package com.nikhil.trading.service;

import com.nikhil.trading.modal.PaymentDetails;
import com.nikhil.trading.modal.User;

public interface PaymentDetailsService {
    public PaymentDetails addPaymentDetails( String accountNumber,
                                             String accountHolderName,
                                             String ifsc,
                                             String bankName,
                                             User user
    );

    public PaymentDetails getUsersPaymentDetails(User user);


}

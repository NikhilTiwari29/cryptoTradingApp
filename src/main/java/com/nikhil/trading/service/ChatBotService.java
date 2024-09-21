package com.nikhil.trading.service;

import com.nikhil.trading.model.CoinDTO;
import com.nikhil.trading.response.ApiResponse;

public interface ChatBotService {
    ApiResponse getCoinDetails(String coinName);

    CoinDTO getCoinByName(String coinName);

    String simpleChat(String prompt);
}

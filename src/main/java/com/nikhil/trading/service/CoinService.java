package com.nikhil.trading.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nikhil.trading.modal.Coin;

import java.util.List;

public interface CoinService {
    List<Coin> getCoinList(String currency,int page) throws Exception;
    String getMarketChart(String coinId,int days) throws Exception;
    String getCoinDetails(String coinId) throws JsonProcessingException;
    Coin findById(String coinId) throws Exception;
    String searchCoin(String keyword);
    String getTop50CoinByMarketCapRank() throws Exception;
    String getTrendingCoin() throws Exception;
}

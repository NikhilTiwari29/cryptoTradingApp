package com.nikhil.trading.service;

import com.nikhil.trading.modal.Coin;

import java.util.List;

public interface CoinService {
    List<Coin> getCoinList(int page);
    String getMarketChart(String coinId,int days);
    String getCoinDetails(String coinId);
    Coin findById(String coinId);
    String searchCoin(String keyword);
    String getTop50CoinByMarketCapRank();
    String getTrendingCoin();
}

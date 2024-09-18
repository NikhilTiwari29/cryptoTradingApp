package com.nikhil.trading.service;


import com.nikhil.trading.modal.Coin;
import com.nikhil.trading.modal.User;
import com.nikhil.trading.modal.Watchlist;

public interface WatchlistService {

    Watchlist findUserWatchlist(Long userId) throws Exception;

    Watchlist createWatchList(User user);

    Watchlist findById(Long id) throws Exception;

    Coin addItemToWatchlist(Coin coin, User user) throws Exception;
}

package com.nikhil.trading.service;

import com.nikhil.trading.model.Coin;
import com.nikhil.trading.model.User;
import com.nikhil.trading.model.Watchlist;

public interface WatchlistService {

    Watchlist findUserWatchlist(Long userId) throws Exception;

    Watchlist createWatchList(User user);

    Watchlist findById(Long id) throws Exception;

    Coin addItemToWatchlist(Coin coin,User user) throws Exception;
}

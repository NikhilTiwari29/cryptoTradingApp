package com.nikhil.trading.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nikhil.trading.modal.Coin;
import com.nikhil.trading.service.CoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/coins")
public class CoinController {

    @Autowired
    private CoinService coinService;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping
    ResponseEntity<List<Coin>> getCoinList(@RequestParam(name = "vs_currency", defaultValue = "usd") String currency,
                                           @RequestParam("page") int page) throws Exception {
        List<Coin> coinList = coinService.getCoinList(currency, page);
        return new ResponseEntity<>(coinList, HttpStatus.ACCEPTED);
    }

}

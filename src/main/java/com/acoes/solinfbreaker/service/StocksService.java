package com.acoes.solinfbreaker.service;

import com.acoes.solinfbreaker.model.Stocks;
import com.acoes.solinfbreaker.repository.StocksRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StocksService {
    private final StocksRepository repository;
    public List<Stocks> getStock(String stockName)  {
        return repository.findByName(stockName);
    }
}
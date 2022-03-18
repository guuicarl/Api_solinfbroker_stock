package com.acoes.solinfbreaker.unitarios;

import com.acoes.solinfbreaker.controller.StocksController;
import com.acoes.solinfbreaker.model.Stocks;
import com.acoes.solinfbreaker.repository.GraficoRepository;
import com.acoes.solinfbreaker.repository.StocksRepository;
import com.acoes.solinfbreaker.service.StocksService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.List;

@ExtendWith(SpringExtension.class)
class StockControllerTeste {
    @InjectMocks
    private StocksController stocksController;
    @Mock
    private StocksRepository repository;
    @Mock
    private StocksService service;
    @Mock
    private GraficoRepository graficoRepository;

    @BeforeEach
    public void setUp(){
        RestAssuredMockMvc.standaloneSetup(this.stocksController);
    }

    @Test
    void teste(){
        List<Stocks> stocks = repository.findAll();
        Assertions.assertThat(stocks).isNotNull();
    }

}

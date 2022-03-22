package com.acoes.solinfbreaker.integracao;

import com.acoes.solinfbreaker.controller.StocksController;
import com.acoes.solinfbreaker.model.Stocks;
import com.acoes.solinfbreaker.repository.GraficoRepository;
import com.acoes.solinfbreaker.repository.StocksRepository;
import com.acoes.solinfbreaker.service.StocksService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class StocksControllerIT {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private StocksController stocksController;
    @Autowired
    private StocksRepository repository;
    @Test
    void getStock() throws Exception {
        String token = "Bearer eyJraWQiOiJGOUxUSktJa2pmMGVzdHFjWVgzM3lXRi1KckVPcWRReHQtb1owT01BaERvIiwiYWxnIjoiUlMyNTYifQ.eyJ2ZXIiOjEsImp0aSI6IkFULk5QVlVHZlBGRy1scnV2RGI2OVU3UXgyS3hicTFDZTBVTE5sbERLbkJoRGciLCJpc3MiOiJodHRwczovL2Rldi0zMDcxNzg2Lm9rdGEuY29tL29hdXRoMi9kZWZhdWx0IiwiYXVkIjoiYXBpOi8vZGVmYXVsdCIsImlhdCI6MTY0Nzg4MjI4OSwiZXhwIjoxNjQ3ODg1ODg5LCJjaWQiOiIwb2EzazhyZzdlYVA1TzN2YjVkNyIsInVpZCI6IjAwdTNqdWF3ZmpsU3B6dHVRNWQ3Iiwic2NwIjpbImVtYWlsIiwib3BlbmlkIiwicHJvZmlsZSJdLCJhdXRoX3RpbWUiOjE2NDc4ODIyODYsInN1YiI6Imd1aWxoZXJtZS5jYXJsb3MwMTRAZ21haWwuY29tIn0.TJmqcmdsu5A5WRfJN_5Kz9D2QpoWgm6n9HO6gnxT-wgD4KoarfD6tsoQfCBV2fHvy1UqjPvM7TqwsPAUOGa-UmOqMsqC-NEJJzs31k1tf5lVJkvd-1pJUdiEQGobG-P2eIJN5zzqGSSixWbT3MRtW5ZFrxCMowk0et2zgiq3QVmYWOthWOTmkvEp8CAPwdf5rRQ2trpDlreOcJD65Vg5nygjO3Osj2GRu0oJnKGAO5_0KPuE4ZbQkLa0kdPBryokhEZkLNPBKu_0s0lCn2B8GnzJ10j_QwTyXDeeK_m8bgPTauibfmlv-Lx6I9G9nqtwAeTCXXskDdOUu3-KvJAldw";
        List<Stocks> stocks = repository.findAll();
        mockMvc.perform(get("/stocks")
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(stocks)))
                .andExpect(status().isOk());


        Assertions.assertThat(stocks).isNotEmpty();
    }

    @Test
    void getStockPorId() {
        Optional<Stocks> stocks = repository.findById(2L);
        Assertions.assertThat(stocks.get().getStockName()).isEqualTo("EMBRAER");
    }

    @Test
    void getStockPorName() throws Exception {
        String token = "Bearer eyJraWQiOiJGOUxUSktJa2pmMGVzdHFjWVgzM3lXRi1KckVPcWRReHQtb1owT01BaERvIiwiYWxnIjoiUlMyNTYifQ.eyJ2ZXIiOjEsImp0aSI6IkFULk5QVlVHZlBGRy1scnV2RGI2OVU3UXgyS3hicTFDZTBVTE5sbERLbkJoRGciLCJpc3MiOiJodHRwczovL2Rldi0zMDcxNzg2Lm9rdGEuY29tL29hdXRoMi9kZWZhdWx0IiwiYXVkIjoiYXBpOi8vZGVmYXVsdCIsImlhdCI6MTY0Nzg4MjI4OSwiZXhwIjoxNjQ3ODg1ODg5LCJjaWQiOiIwb2EzazhyZzdlYVA1TzN2YjVkNyIsInVpZCI6IjAwdTNqdWF3ZmpsU3B6dHVRNWQ3Iiwic2NwIjpbImVtYWlsIiwib3BlbmlkIiwicHJvZmlsZSJdLCJhdXRoX3RpbWUiOjE2NDc4ODIyODYsInN1YiI6Imd1aWxoZXJtZS5jYXJsb3MwMTRAZ21haWwuY29tIn0.TJmqcmdsu5A5WRfJN_5Kz9D2QpoWgm6n9HO6gnxT-wgD4KoarfD6tsoQfCBV2fHvy1UqjPvM7TqwsPAUOGa-UmOqMsqC-NEJJzs31k1tf5lVJkvd-1pJUdiEQGobG-P2eIJN5zzqGSSixWbT3MRtW5ZFrxCMowk0et2zgiq3QVmYWOthWOTmkvEp8CAPwdf5rRQ2trpDlreOcJD65Vg5nygjO3Osj2GRu0oJnKGAO5_0KPuE4ZbQkLa0kdPBryokhEZkLNPBKu_0s0lCn2B8GnzJ10j_QwTyXDeeK_m8bgPTauibfmlv-Lx6I9G9nqtwAeTCXXskDdOUu3-KvJAldw";
        Optional<Stocks> stocks = repository.findById(2L);
        mockMvc.perform(get("/{stockName}", "EMBRAER")
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(stocks)))
            .andExpect(status().isOk());

    }

}

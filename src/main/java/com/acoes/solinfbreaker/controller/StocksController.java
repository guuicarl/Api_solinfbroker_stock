package com.acoes.solinfbreaker.controller;


import com.acoes.solinfbreaker.dto.StockDto;
import com.acoes.solinfbreaker.model.Grafico;
import com.acoes.solinfbreaker.model.Stocks;
import com.acoes.solinfbreaker.repository.GraficoRepository;
import com.acoes.solinfbreaker.repository.StocksRepository;
import com.acoes.solinfbreaker.service.StocksService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

@CrossOrigin(origins = { "http://localhost:8082", "http://localhost:8081"})
@RestController
public class StocksController {
    private static final Logger logger = LoggerFactory.getLogger(StocksController.class);
    @Autowired
    private StocksRepository stocksRepository;
    @Autowired
    private StocksService service;
    @Autowired
    private GraficoRepository graficoRepository;


    @GetMapping("/stocks/{id}")
    public Optional<Stocks> obterStock(@PathVariable(value = "id")Long id) {
        return stocksRepository.findById(id);
    }

    @GetMapping("/historico/{idStock}")
    public List<Grafico> obterGrafico(@PathVariable(value = "idStock")Stocks idStock) {
        return graficoRepository.findByStock(idStock);
    }

    @GetMapping("/{stockName}")
    public ResponseEntity<List<Stocks>> getStocks(@PathVariable("stockName") String stockName) {
        try {
            return ResponseEntity.ok().body(service.getStock(stockName));
        }  catch (Exception e) {
            if(e.getMessage().equals("Stock_not_found"))
                return ResponseEntity.notFound().build();
            return ResponseEntity.badRequest().build();
        }
    }
    private List<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    @GetMapping(value = "/temporeal")
    public SseEmitter temporeal(HttpServletResponse response){
        response.setHeader("Cache_Control", "no-store");
        SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);
        try {
            emitters.add(sseEmitter);
        } catch (Exception e){
            logger.error("Nao deu certo o tempo real");
        }
        sseEmitter.onCompletion(() -> emitters.remove(sseEmitter));
        return sseEmitter;
    }

    @GetMapping("/updated")
    public List<Stocks> listar10(){
        return stocksRepository.findStocks();
    }


    @GetMapping("/stocks")
    public List<Stocks> listar(){
        return stocksRepository.findAll();
    }

    @PostMapping("/teste")
    public ResponseEntity<Stocks> teste(@RequestBody StockDto stockDto){
        Stocks stock = stocksRepository.findById(stockDto.getId()).orElseThrow();
        if(stockDto.getAskMax() != null) {
            stock.setAskMax(stockDto.getAskMax());
            stock.setAskMin(stockDto.getAskMin());
        }
        if (stockDto.getBidMin() != null) {
            stock.setBidMax(stockDto.getBidMax());
            stock.setBidMin(stockDto.getBidMin());
        }
         stock = stocksRepository.save(stock);
        publicar();
        atualizaPrices(stock);
        return new ResponseEntity<>(stock, HttpStatus.CREATED);
    }

    private void atualizaPrices(Stocks stocks) {
        Date date = new Date();
        Optional<Grafico> historico = graficoRepository.findByIdAndDate(stocks.getId(), new Timestamp(date.getTime()));

        if(historico.isPresent()) {
            if (historico.get().getHigh() < stocks.getAskMin()) {
                historico.get().setHigh(stocks.getAskMin());
            }
            if (historico.get().getLow() > stocks.getAskMin()) {
                historico.get().setLow(stocks.getAskMin());
            }
            historico.get().setFechado(stocks.getAskMin());
            graficoRepository.save(historico.get());
        }
        else if (stocks.getAskMin() == null){
            logger.error("Não pode ser criado");
        } else {
            graficoRepository.save(new Grafico(stocks));
        }
    }

    public void publicar(){
        for(SseEmitter emitter : emitters) {
            try {
                emitter.send(stocksRepository.findStocks());
            } catch (IOException e){
                emitters.remove(emitter);
            }
        }
    }

}

package com.acoes.solinfbreaker.controller;

import com.acoes.solinfbreaker.model.Grafico;
import com.acoes.solinfbreaker.model.Stocks;
import com.acoes.solinfbreaker.repository.GraficoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = { "http://localhost:8082", "http://localhost:8081"})
@RestController
public class GraficoController {
    @Autowired
    private GraficoRepository repository;

    @GetMapping("/historico")
    public List<Grafico> getGrafico(@PathVariable(value = "id_stock") Stocks idStock){
        return repository.findByStock(idStock);
    }
}

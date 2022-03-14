package com.acoes.solinfbreaker.controller;

import com.acoes.solinfbreaker.model.Grafico;
import com.acoes.solinfbreaker.repository.GraficoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class GraficoController {
    @Autowired
    private GraficoRepository repository;

    @PostMapping("/historico")
    public Grafico adicionar(@RequestBody Grafico grafico){
        return repository.save(grafico);
    }
}

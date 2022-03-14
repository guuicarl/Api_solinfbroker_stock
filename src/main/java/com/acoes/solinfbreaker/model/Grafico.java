package com.acoes.solinfbreaker.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Data
@RequiredArgsConstructor
@Entity
@Table(name = "grafico")
public class Grafico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long id_stock;
    private Double aberto;
    private Double fechado;
    private Double high;
    private Double low;
    @CreationTimestamp
    @Column(name = "created_on")
    private Timestamp created_on;

    public Grafico(Stocks stocks) {
        Date date = new Date();
        this.id_stock = stocks.getId();
        this.aberto = stocks.getAsk_min();
        this.fechado = stocks.getAsk_min();
        this.high = stocks.getAsk_min();
        this.low = stocks.getAsk_min();
        this.created_on = new Timestamp(date.getTime());
    }
}

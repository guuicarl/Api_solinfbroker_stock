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
    @Column(name = "id_stock")
    private Long idStock;
    private Double aberto;
    private Double fechado;
    private Double high;
    private Double low;
    @CreationTimestamp
    @Column(name = "created_on")
    private Timestamp created;

    public Grafico(Stocks stocks) {
        Date date = new Date();
        this.idStock = stocks.getId();
        this.aberto = stocks.getAskMin();
        this.fechado = stocks.getAskMin();
        this.high = stocks.getAskMin();
        this.low = stocks.getAskMin();
        this.created = new Timestamp(date.getTime());
    }
}

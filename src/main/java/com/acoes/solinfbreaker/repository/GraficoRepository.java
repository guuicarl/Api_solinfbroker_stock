package com.acoes.solinfbreaker.repository;

import com.acoes.solinfbreaker.model.Grafico;
import com.acoes.solinfbreaker.model.Stocks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public interface GraficoRepository extends JpaRepository<Grafico, Long> {
    @Query( nativeQuery = true, value = """
    select * from grafico as gra where
    gra.id_stock = :id_stock and 
    date_trunc('hour', gra.created_on) = date_trunc('hour', cast (:now as Timestamp))
""")
    Optional<Grafico> findByIdAndDate(@Param("id_stock") Long id_stock, @Param("now") Timestamp agora);

    @Query(value = "select * from grafico where id_stock = ?1", nativeQuery = true)
    List<Grafico> findByStock(Long id_stock);
}

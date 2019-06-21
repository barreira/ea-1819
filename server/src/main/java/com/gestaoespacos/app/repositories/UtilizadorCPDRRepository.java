package com.gestaoespacos.app.repositories;

import com.gestaoespacos.app.model.UtilizadorCPDR;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UtilizadorCPDRRepository extends JpaRepository<UtilizadorCPDR, Long> {

    @Query("SELECT u FROM UtilizadorCPDR u JOIN u.pedidos p WHERE p.id = ?1")
    Optional<UtilizadorCPDR> findByPedido(long nr_pedido);
}

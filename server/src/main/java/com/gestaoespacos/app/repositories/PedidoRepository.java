package com.gestaoespacos.app.repositories;

import com.gestaoespacos.app.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    @Query("SELECT p FROM UtilizadorCPDR u JOIN u.pedidos p WHERE u.id = ?1 AND p.atendido = ?2")
    List<Pedido> findAllByAtendido(long ucpdr, boolean atendido);
}

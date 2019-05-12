package com.gestaoespacos.app.repositories;

import com.gestaoespacos.app.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

}

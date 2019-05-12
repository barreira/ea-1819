package com.gestaoespacos.app.repositories;

import com.gestaoespacos.app.model.Evento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventoRepository extends JpaRepository<Evento, Long> {
}

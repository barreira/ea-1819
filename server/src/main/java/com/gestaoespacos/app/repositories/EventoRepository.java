package com.gestaoespacos.app.repositories;

import com.gestaoespacos.app.model.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface EventoRepository extends JpaRepository<Evento, Long> {
    List<Evento> findByNome(String nome);
}

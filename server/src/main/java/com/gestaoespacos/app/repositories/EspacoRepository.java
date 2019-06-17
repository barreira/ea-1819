package com.gestaoespacos.app.repositories;

import com.gestaoespacos.app.model.Espaco;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EspacoRepository extends JpaRepository<Espaco, Long> {
    List<Espaco> findByDesignacao(String designacao);
}

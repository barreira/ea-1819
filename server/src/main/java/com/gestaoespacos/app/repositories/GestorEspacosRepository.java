package com.gestaoespacos.app.repositories;

import com.gestaoespacos.app.model.GestorEspacos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GestorEspacosRepository extends JpaRepository<GestorEspacos, Long> {
    Optional<GestorEspacos> findByUsername(String username);
}
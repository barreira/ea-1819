package com.gestaoespacos.app.repositories;

import com.gestaoespacos.app.model.Administrador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdministradorRepository extends JpaRepository<Administrador, Long> {
    Optional<Administrador> findByUsername(String username);
}

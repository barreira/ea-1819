package com.gestaoespacos.app.repositories;

import com.gestaoespacos.app.model.Utilizador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Utilizador, Long> {
    Optional<Utilizador> findByUsername(String username);
}

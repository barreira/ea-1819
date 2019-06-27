package com.gestaoespacos.app.repositories;

import com.gestaoespacos.app.model.Ator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AtorRepository extends JpaRepository<Ator, Long> {
    Optional<Ator> findByUsername(String username);
}
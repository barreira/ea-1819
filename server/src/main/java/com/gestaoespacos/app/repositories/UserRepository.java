package com.gestaoespacos.app.repositories;

import com.gestaoespacos.app.model.Utilizador;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Utilizador, Long> {

}

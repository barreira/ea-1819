package com.gestaoespacos.app.repositories;

import com.gestaoespacos.app.model.Espaco;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EspacoRepository extends JpaRepository<Espaco, Long> {
}

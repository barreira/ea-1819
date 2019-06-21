package com.gestaoespacos.app.repositories;

import com.gestaoespacos.app.model.Alocacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlocacaoRepository extends JpaRepository<Alocacao, Long> {
}

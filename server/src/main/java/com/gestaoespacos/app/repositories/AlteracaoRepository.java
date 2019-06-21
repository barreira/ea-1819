package com.gestaoespacos.app.repositories;

import com.gestaoespacos.app.model.Alteracao;
import com.gestaoespacos.app.model.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface AlteracaoRepository extends JpaRepository<Alteracao, Long> {
    @Transactional
    Long deleteByEvento(Evento e);
}

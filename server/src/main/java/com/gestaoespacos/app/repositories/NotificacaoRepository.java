package com.gestaoespacos.app.repositories;

import com.gestaoespacos.app.model.Notificacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificacaoRepository extends JpaRepository<Notificacao, Long> {
}

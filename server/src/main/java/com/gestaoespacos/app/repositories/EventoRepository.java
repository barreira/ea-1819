package com.gestaoespacos.app.repositories;

import com.gestaoespacos.app.model.Espaco;
import com.gestaoespacos.app.model.Evento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.*;

public interface EventoRepository extends JpaRepository<Evento, Long> {
    List<Evento> findByNome(String nome);
    Set<Evento> findAllByDateTimeInicialGreaterThanEqualAndDateTimeFinalLessThanEqualAndPeriodicidadeEquals(LocalDateTime inicio, LocalDateTime fim, int periodo);
    Set<Evento> findAllByPeriodicidadeGreaterThanAndLimiteGreaterThanEqual(int periodo, LocalDateTime limite);
    Set<Evento> findAllByDateTimeInicialGreaterThanEqualAndDateTimeFinalLessThanEqualAndPeriodicidadeEqualsAndEspaco(LocalDateTime inicio, LocalDateTime fim, int periodo, Espaco e);
    Set<Evento> findAllByPeriodicidadeGreaterThanAndLimiteGreaterThanEqualAndEspaco(int periodo, LocalDateTime limite, Espaco e);
}

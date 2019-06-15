package com.gestaoespacos.app.model;

import java.util.List;

public interface Responsavel {
    void cancelaEvento(long id_evt);
    List<Evento> meusEventos();
}

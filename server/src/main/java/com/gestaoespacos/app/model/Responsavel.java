package com.gestaoespacos.app.model;

import java.util.List;

public interface Responsavel {
    void cancelaEvento(Evento e);
    List<Evento> meusEventos();
}

package com.gestaoespacos.app.controllers.model;

import com.gestaoespacos.app.model.Evento;

public class EventoUpdate {

    private long id;
    private Evento novoEvento;

    public EventoUpdate(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Evento getNovoEvento() {
        return novoEvento;
    }

    public void setNovoEvento(Evento novoEvento) {
        this.novoEvento = novoEvento;
    }

    @Override
    public String toString() {
        return "EventoUpdate{" +
                "id=" + id +
                ", novoEvento=" + novoEvento +
                '}';
    }
}

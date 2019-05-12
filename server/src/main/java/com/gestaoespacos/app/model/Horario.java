package com.gestaoespacos.app.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Horario {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany
    @JoinColumn(name = "horario_id")
    private List<Evento> eventos;

    public Horario(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Evento> getEventos() {
        return eventos;
    }

    public void setEventos(List<Evento> eventos) {
        this.eventos = eventos;
    }
}


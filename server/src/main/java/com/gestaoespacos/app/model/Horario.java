package com.gestaoespacos.app.model;

import org.apache.tomcat.jni.Local;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Entity
public class Horario {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany
    @JoinColumn(name = "horario_id")
    private Map<LocalDate, Evento> eventos;

    public Horario(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Map<LocalDate, Evento> getEventos() {
        return eventos;
    }

    public void setEventos(Map<LocalDate, Evento> eventos) {
        this.eventos = eventos;
    }

    public void addEvento(LocalDate data, long id_evt){

    }

    public void removeEvento(LocalDate data, long id_evt){

    }
}


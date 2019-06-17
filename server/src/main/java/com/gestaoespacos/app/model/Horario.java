package com.gestaoespacos.app.model;

import com.gestaoespacos.app.repositories.EventoRepository;
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

    public void addEvento(long id_evt){
        Evento e = null;// = EventoRepository.getOne(id_evt);

        eventos.put(e.getDateTimeFinal().toLocalDate(), e);

        //save?
    }

    public void removeEvento(long id_evt){
        Evento e = null;// = EventoRepository.getOne(id_evt);

        eventos.remove(e.getDateTimeFinal().toLocalDate());
    }
}


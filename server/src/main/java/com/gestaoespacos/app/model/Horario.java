package com.gestaoespacos.app.model;

import com.gestaoespacos.app.repositories.EventoRepository;
import org.apache.tomcat.jni.Local;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Entity
public class Horario {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany
    @JoinColumn(name = "horario_id")
    //private Map<LocalDate, List<Evento>> eventos;
    private List<Evento> eventos;

    public Horario(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    /*public Map<LocalDate, List<Evento>> getEventos() {
        return eventos;
    }

    public void setEventos(Map<LocalDate, List<Evento>> eventos) {
        this.eventos = eventos;
    }

    public void addEvento(Evento e){
        LocalDate data = e.getDateTimeInicial().toLocalDate();

        if(eventos.containsKey(data)){
            List es = eventos.get(data);
            es.add(e);
        }
        else{
            List es = new ArrayList<Evento>();
            es.add(e);
            eventos.put(data, es);
        }


    }

    public void removeEvento(Evento e){
        LocalDate data = e.getDateTimeInicial().toLocalDate();

        if(eventos.containsKey(data)){
            List es = eventos.get(data);
            es.remove(e);
        }
    }*/

    public List<Evento> getEventos() {
        return eventos;
    }

    public void setEventos(List<Evento> eventos) {
        this.eventos = eventos;
    }

    public void addEvento(Evento e){
        eventos.add(e);
    }

    public void removeEvento(Evento e){
        eventos.remove(e);
    }

    @Override
    public String toString() {
        return "Horario{" +
                "id=" + id +
                ", eventos=" + eventos +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Horario horario = (Horario) o;
        return getId() == horario.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}


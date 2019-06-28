package com.gestaoespacos.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gestaoespacos.app.repositories.EventoRepository;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Espaco {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private long id;

    private String designacao;

    @OneToOne
    //@JoinColumn(name = "espaco_id")
    @JsonIgnore
    private Horario horario;

    public Espaco(){

    }

    public Espaco(long id, String designacao) {
        this.id = id;
        this.designacao = designacao;
    }

    public Horario getHorario() {
        return horario;
    }

    public void setHorarios(Horario horario) {
        this.horario = horario;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDesignacao() {
        return designacao;
    }

    public void setDesignacao(String designacao) {
        this.designacao = designacao;
    }

    public void addEvento(Evento e){
        horario.addEvento(e);
    }

    public void removeEvento(Evento e){
       horario.removeEvento(e);
    }

    @Override
    public String toString() {
        return "EspacoRepository{" +
                "id=" + id +
                ", designacao='" + designacao + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Espaco espaco = (Espaco) o;
        return getId() == espaco.getId() &&
                Objects.equals(getDesignacao(), espaco.getDesignacao()) &&
                Objects.equals(getHorario(), espaco.getHorario());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDesignacao(), getHorario());
    }
}

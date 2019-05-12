package com.gestaoespacos.app.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Espaco {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private long id;

    private String designacao;

    @OneToMany
    @JoinColumn(name = "espaco_id")
    private List<Horario> horarios;

    public Espaco(){

    }

    public Espaco(long id, String designacao) {
        this.id = id;
        this.designacao = designacao;
    }

    public List<Horario> getHorarios() {
        return horarios;
    }

    public void setHorarios(List<Horario> horarios) {
        this.horarios = horarios;
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

    @Override
    public String toString() {
        return "EspacoRepository{" +
                "id=" + id +
                ", designacao='" + designacao + '\'' +
                '}';
    }
}

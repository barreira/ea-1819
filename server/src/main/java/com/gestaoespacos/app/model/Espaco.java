package com.gestaoespacos.app.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Espaco {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private long id;

    private String designacao;

    @OneToOne
    //@JoinColumn(name = "espaco_id")
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

    public void addEvento(long id_evt){

    }

    public void removeEvento(long id_evt){

    }

    @Override
    public String toString() {
        return "EspacoRepository{" +
                "id=" + id +
                ", designacao='" + designacao + '\'' +
                '}';
    }
}

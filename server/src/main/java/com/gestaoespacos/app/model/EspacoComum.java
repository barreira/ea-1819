package com.gestaoespacos.app.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class EspacoComum {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String descricao;

    @OneToMany
    @JoinColumn(name = "espacocomum_id")
    private List<Espaco> espacos;


    public EspacoComum(){
    }

    public EspacoComum(long id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public List<Espaco> getEspacos() {
        return espacos;
    }

    public void setEspacos(List<Espaco> espacos) {
        this.espacos = espacos;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return "EspacoComumRepository{" +
                "id=" + id +
                ", descricao='" + descricao + '\'' +
                '}';
    }
}

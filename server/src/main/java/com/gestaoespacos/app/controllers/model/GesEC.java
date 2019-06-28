package com.gestaoespacos.app.controllers.model;

import com.gestaoespacos.app.model.Espaco;

import java.util.List;

public class GesEC {
    private Long id;
    private String designacao;
    private List<Espaco> espacos;

    public GesEC(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDesignacao() {
        return designacao;
    }

    public void setDesignacao(String designacao) {
        this.designacao = designacao;
    }

    public List<Espaco> getEspacos() {
        return espacos;
    }

    public void setEspacos(List<Espaco> espacos) {
        this.espacos = espacos;
    }

    @Override
    public String toString() {
        return "GesEC{" +
                "id=" + id +
                ", designacao='" + designacao + '\'' +
                ", espacos=" + espacos +
                '}';
    }
}

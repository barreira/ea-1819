package com.gestaoespacos.app.controllers.model;

import com.gestaoespacos.app.model.Alocacao;

public class PedidoAlocacao {

    //id do utilizadorcdpr
    private long id;
    private Alocacao alocacao;

    public PedidoAlocacao() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Alocacao getAlocacao() {
        return alocacao;
    }

    public void setAlocacao(Alocacao alocacao) {
        this.alocacao = alocacao;
    }

    @Override
    public String toString() {
        return "PedidoAlocacao{" +
                "id=" + id +
                ", alocacao=" + alocacao +
                '}';
    }
}

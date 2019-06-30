package com.gestaoespacos.app.controllers.model;

import com.gestaoespacos.app.model.Alteracao;

public class PedidoAlteracao {

    //id do utilizadorcdpr
    private long id;
    private Alteracao alteracao;

    public PedidoAlteracao() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Alteracao getAlteracao() {
        return alteracao;
    }

    public void setAlteracao(Alteracao alocacao) {
        this.alteracao = alocacao;
    }

    @Override
    public String toString() {
        return "PedidoAlocacao{" +
                "id=" + id +
                ", alocacao=" + alteracao +
                '}';
    }
}

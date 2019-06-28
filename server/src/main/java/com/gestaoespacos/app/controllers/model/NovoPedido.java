package com.gestaoespacos.app.controllers.model;

import com.gestaoespacos.app.model.Pedido;

public class NovoPedido {

    private long id_usercpdr;
    private Pedido p;

    public NovoPedido(){}

    public long getId_usercpdr() {
        return id_usercpdr;
    }

    public void setId_usercpdr(long id_usercpdr) {
        this.id_usercpdr = id_usercpdr;
    }

    public Pedido getPedido() {
        return p;
    }

    public void setP(Pedido p) {
        this.p = p;
    }

    @Override
    public String toString() {
        return "NovoPedido{" +
                "id_usercpdr=" + id_usercpdr +
                ", p=" + p +
                '}';
    }
}

package com.gestaoespacos.app.model;

import javax.persistence.*;

@Entity
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private boolean aceite;

    @OneToOne
    private Evento evento;

    public Pedido() {
    }

    public Pedido(long id, boolean aceite) {
        this.id = id;
        this.aceite = aceite;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isAceite() {
        return aceite;
    }

    public void setAceite(boolean aceite) {
        this.aceite = aceite;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", aceite=" + aceite +
                '}';
    }
}

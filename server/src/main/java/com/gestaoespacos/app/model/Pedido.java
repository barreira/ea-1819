package com.gestaoespacos.app.model;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
        name="tipoPedido",
        discriminatorType = DiscriminatorType.CHAR
)
public abstract class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private boolean aceite;
    private boolean atendido;

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

    public boolean isAtendido() { return atendido; }

    public void setAtendido(boolean atendido) { this.atendido = atendido; }

    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", aceite=" + aceite +
                ", atendido=" + atendido +
                '}';
    }
}

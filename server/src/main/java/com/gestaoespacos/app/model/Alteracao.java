package com.gestaoespacos.app.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.DiscriminatorValue;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("U")
public class Alteracao extends Pedido {

    //Identificador do evento existente
    //que terá as suas informações atualizadas
    //se o pedido for aceite
    /*private long id_evt;

    public long getId_evt() {
        return id_evt;
    }

    public void setId_evt(long id_evt) {
        this.id_evt = id_evt;
    }*/

    public Alteracao(){
        super();
    }

    public Alteracao(String nome, LocalDateTime inicio, LocalDateTime fim, String descricao, int periodo, LocalDateTime limite, Espaco e, Evento ev) {
        super(nome, inicio, fim, descricao, periodo, limite, e);
        this.evento = ev;
    }

    //Evento existente,
    //que terá as suas informações atualizadas
    //se o pedido for aceite
    @OneToOne
    private Evento evento;

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }


}

package com.gestaoespacos.app.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class UtilizadorCPDR extends Utilizador {

    @OneToMany
    @JoinColumn(name = "utilizadorCPDR_id")
    private List<Evento> eventos;

    @OneToMany
    @JoinColumn(name = "utilizadorCPDR_id")
    private List<Pedido> pedidos;

    public UtilizadorCPDR(){
        super();
    }

    public List<Evento> getEventos() {
        return eventos;
    }

    public void setEventos(List<Evento> eventos) {
        this.eventos = eventos;
    }
}

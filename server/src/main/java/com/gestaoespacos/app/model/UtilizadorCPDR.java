package com.gestaoespacos.app.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class UtilizadorCPDR extends Utilizador implements Responsavel{

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

    public Pedido alocarEspaco(Alocacao a){
        return null;
    }

    public Pedido alterarEvento(Alteracao a){
        return null;
    }

    public Pedido cancelaPedido(long nr_pedido){
        return null;
    }

    @Override
    public void cancelaEvento(long id_evt) {

    }

    @Override
    public List<Evento> meusEventos() {
        return null;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }
}

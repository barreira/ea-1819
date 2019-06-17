package com.gestaoespacos.app.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class UtilizadorCPDR extends Utilizador implements Responsavel{

    @OneToMany
    @JoinColumn(name = "utilizadorCPDR_id")
    private List<Evento> eventos;

    @OneToMany
    @JoinColumn(name = "utilizadorCPDR_id")
    private Set<Pedido> pedidos;

    public UtilizadorCPDR(){
        super();
    }

    /*public List<Evento> getEventos() {
        return eventos;
    }*/

    public void setEventos(List<Evento> eventos) {
        this.eventos = eventos;
    }

    public Pedido alocarEspaco(Alocacao a){
        pedidos.add(a);
        return a;
    }

    public Pedido alterarEvento(Alteracao a){
        pedidos.add(a);
        return a;
    }

    public Pedido cancelaPedido(Pedido p){
        pedidos.remove(p);
        return p;
    }

    @Override
    public void cancelaEvento(Evento e) {
        eventos.remove(e);
    }

    @Override
    public List<Evento> meusEventos() {
        return eventos;
    }

    public Set<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(Set<Pedido> pedidos) {
        this.pedidos = pedidos;
    }
}

package com.gestaoespacos.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UtilizadorCPDR extends Utilizador implements Responsavel{

    @OneToMany(mappedBy = "utilizadorResponsavel")
    //@JoinColumn(name = "utilizadorCPDR_id")
    private List<Evento> eventos;
    //Eventos pelos quais o utilizador é responsavel

    @OneToMany //todo:
    @JoinColumn(name = "utilizadorCPDR_id")
    @JsonIgnore
    private Set<Pedido> pedidos;

    public UtilizadorCPDR(){
        super();
    }

    public UtilizadorCPDR(String username, String password, String email, String nome) {
        super(username, password, email, nome);
        eventos = new ArrayList<>();
        pedidos = new HashSet<>();
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

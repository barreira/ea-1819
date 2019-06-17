package com.gestaoespacos.app.model;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class GestorEspacos extends Ator implements Responsavel{

    @OneToMany
    @JoinColumn(name = "gestorespacos_id")
    private List<EspacoComum> espacosComuns;

    @OneToMany
    @JoinColumn(name = "gestorespacos_id")
    private List<Evento> eventos;

    @OneToMany
    @JoinColumn(name = "gestorespacos_id")
    private List<Pedido> pedidos;

    public GestorEspacos(){

    }

    public GestorEspacos(String username, String password, List<EspacoComum> espacosComuns, List<Evento> eventos) {
        super(username, password);
        this.espacosComuns = espacosComuns;
        this.eventos = eventos;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    public List<EspacoComum> getEspacosComuns() {
        return espacosComuns;
    }

    public void setEspacosComuns(List<EspacoComum> espacosComuns) {
        this.espacosComuns = espacosComuns;
    }

    public void setEventos(List<Evento> eventos) {
        this.eventos = eventos;
    }

    @Override
    public void cancelaEvento(Evento e) {
        eventos.remove(e);
    }

    @Override
    public List<Evento> meusEventos() {
        return eventos.stream()
                      .filter(e -> e.getUtilizadorResponsavel().equals(this))
                      .collect(Collectors.toList());
    }

    public Pedido aceitaPedido(Pedido p){
        pedidos.remove(p);

        p.setAceite(true);
        p.setAtendido(true);

        return p;
    }

    public Pedido rejeitaPedido(Pedido p){
        pedidos.remove(p);

        p.setAceite(false);
        p.setAtendido(true);

        return p;
    }

    public void novoEvento(Evento e){
        eventos.add(e);
    }

    public void updateEvento(Evento antigo, Evento novo_evt){
        eventos.remove(antigo);
        eventos.add(novo_evt);
    }

    public EspacoComum novoEC(String d, List<Espaco> ec){
        EspacoComum e = new EspacoComum(d, ec);
        espacosComuns.add(e);
        return e;
    }

    public EspacoComum updateEC(EspacoComum e, String d, List<Espaco> ec){
        e.setDescricao(d);
        e.setEspacos(ec);

        //e é uma referencia certo?
        //nao é preciso "remover" o espaco da lista 'eventos'
        //e "adicionar", pq o hibernate trata de sincronizar 'eventos' com o estado da BD certo?

        return e;
    }

    public EspacoComum removeEC(EspacoComum ec){
        espacosComuns.remove(ec);
        return ec;
    }
}

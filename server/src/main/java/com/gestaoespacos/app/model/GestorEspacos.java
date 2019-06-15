package com.gestaoespacos.app.model;

import javax.persistence.*;
import java.util.List;

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

    public List<Evento> getEventos() {
        return eventos;
    }

    public void setEventos(List<Evento> eventos) {
        this.eventos = eventos;
    }

    @Override
    public void cancelaEvento(long id_evt) {

    }

    @Override
    public List<Evento> meusEventos() {
        return null;
    }

    public Pedido aceitaPedido(long nr_pedido){
        return null;
    }

    public Pedido rejeitaPedido(long nr_pedido){
        return null;
    }

    public void novoEvento(Evento e){

    }

    public void updateEvento(long id_evt, Evento novo_evt){

    }

    public EspacoComum novoEC(String d, List<Espaco> ec){
        return null;
    }

    public EspacoComum updateEC(long id_ec, String d, List<Espaco> ec){
        return null;
    }

    public EspacoComum removeEC(long id_ec){
        return null;
    }
}

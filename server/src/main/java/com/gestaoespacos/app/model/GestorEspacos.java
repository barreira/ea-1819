package com.gestaoespacos.app.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class GestorEspacos {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private long id;

    private String username;
    private String password;

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

    public GestorEspacos(long id, String username, String password, List<EspacoComum> espacosComuns, List<Evento> eventos) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.espacosComuns = espacosComuns;
        this.eventos = eventos;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
}

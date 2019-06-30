package com.gestaoespacos.app.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Entity
public class GestorEspacos extends Ator implements Responsavel{

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "gestorespacos_id")
    private Set<EspacoComum> espacosComuns;

    @OneToMany(mappedBy = "utilizadorResponsavel", fetch = FetchType.EAGER)
    //@JoinColumn(name = "gestorespacos_id")
    //Eventos pelos quais o gestor é responsável
    private List<Evento> eventos;

    /*@OneToMany
    @JoinColumn(name = "gestorespacos_id")
    private List<Pedido> pedidos;*/

    public GestorEspacos(){

    }

    public GestorEspacos(String username, String password, Set<EspacoComum> espacosComuns, List<Evento> eventos) {
        super(username, password);
        this.espacosComuns = espacosComuns;
        this.eventos = eventos;
    }

    /*public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }*/

    public Set<EspacoComum> getEspacosComuns() {
        return espacosComuns;
    }

    public void setEspacosComuns(Set<EspacoComum> espacosComuns) {
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
        return eventos;
    }

    public Pedido aceitaPedido(Pedido p){
        p.setAceite(true);
        p.setAtendido(true);

        return p;
    }

    public Pedido rejeitaPedido(Pedido p){
        p.setAceite(false);
        p.setAtendido(true);

        return p;
    }

    public void updateEvento(Evento antigo, Evento novo_evt, Espaco esp){
        if(novo_evt.getNome() != null) antigo.setNome(novo_evt.getNome());
        if(novo_evt.getDateTimeInicial() != null) antigo.setDateTimeInicial(novo_evt.getDateTimeInicial());
        if(novo_evt.getDateTimeFinal() != null) antigo.setDateTimeFinal(novo_evt.getDateTimeFinal());
        if(novo_evt.getDescricao() != null) antigo.setDescricao(novo_evt.getDescricao());
        if(esp != null){
            antigo.setEspaco(esp);
            esp.getHorario().addEvento(antigo);
        }
        antigo.setPeriodicidade(novo_evt.getPeriodicidade());
        if(novo_evt.getLimite() != null) antigo.setLimite(novo_evt.getLimite());
        if(novo_evt.getSeguidores() != null) antigo.setSeguidores(novo_evt.getSeguidores());
        if(novo_evt.getUtilizadorResponsavel() != null) antigo.setUtilizadorResponsavel(novo_evt.getUtilizadorResponsavel());
    }

    public void updateEvento(Evento antigo, Evento novo_evt){
        updateEvento(antigo, novo_evt, null);
    }

    public EspacoComum novoEC(String d, List<Espaco> ec){
        EspacoComum e = new EspacoComum(d, ec);
        espacosComuns.add(e);
        return e;
    }

    public EspacoComum updateEC(EspacoComum e, String d, List<Espaco> ec){
        e.setDescricao(d);
        e.setEspacos(ec);
        return e;
    }

    public EspacoComum removeEC(EspacoComum ec){
        espacosComuns.remove(ec);
        return ec;
    }

}

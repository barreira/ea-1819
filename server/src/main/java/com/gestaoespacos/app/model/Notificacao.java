package com.gestaoespacos.app.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Notificacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String justificacao;
    private LocalDateTime momento;

    //@OneToOne
    //@JoinColumn(name = "notificacao_id")
    //private Evento evento;

    public Notificacao() {
    }

    public Notificacao(String justificacao) {
        //this.id = id;
        this.justificacao = justificacao;
        this.momento = LocalDateTime.now();
        //this.evento = e;
    }

    /*public Evento getEvento() {
        return evento;
    }*/

    /*public void setEvento(Evento evento) {
        this.evento = evento;
    }*/

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getJustificacao() {
        return justificacao;
    }

    public void setJustificacao(String justificacao) {
        this.justificacao = justificacao;
    }

    public LocalDateTime getMomento() {
        return momento;
    }

    public void setMomento(LocalDateTime momento) {
        this.momento = momento;
    }

    @Override
    public String toString() {
        return "Notificacao{" +
                "id=" + id +
                ", justificacao='" + justificacao + '\'' +
               // ", evento(nome)=" + evento.getNome() +
                ", moment=" + momento +
                '}';
    }
}

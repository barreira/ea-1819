package com.gestaoespacos.app.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Notificacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String justificacao;

    @OneToMany
    @JoinColumn(name = "notificacao_id")
    private List<Evento> eventos;

    public Notificacao() {
    }

    public Notificacao(long id, String justificacao) {
        this.id = id;
        this.justificacao = justificacao;
    }

    public List<Evento> getEventos() {
        return eventos;
    }

    public void setEventos(List<Evento> eventos) {
        this.eventos = eventos;
    }

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

    @Override
    public String toString() {
        return "Notificacao{" +
                "id=" + id +
                ", justificacao='" + justificacao + '\'' +
                ", eventos=" + eventos +
                '}';
    }
}

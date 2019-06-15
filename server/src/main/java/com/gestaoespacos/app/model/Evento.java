package com.gestaoespacos.app.model;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String nome;

    private LocalDateTime dateTimeInicial;
    private LocalDateTime dateTimeFinal;

    private String descricao;

    private int periodicidade;

    @OneToMany
    @JoinColumn(name = "evento_id")
    private List<Utilizador> seguidores;

    @OneToOne
    private Responsavel utilizadorResponsavel;


    // TODO: Duvida, nao podera haver varios espacos por evento?!
    @OneToOne
    private Evento evento;

    public Evento() {
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public Responsavel getUtilizadorResponsavel() {
        return utilizadorResponsavel;
    }

    public void setUtilizadorResponsavel(Responsavel utilizadorResponsavel) {
        this.utilizadorResponsavel = utilizadorResponsavel;
    }

    public List<Utilizador> getSeguidores() {
        return seguidores;
    }

    public void setSeguidores(List<Utilizador> seguidores) {
        this.seguidores = seguidores;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDateTime getDateTimeInicial() {
        return dateTimeInicial;
    }

    public void setDateTimeInicial(LocalDateTime dateTimeInicial) {
        this.dateTimeInicial = dateTimeInicial;
    }

    public LocalDateTime getDateTimeFinal() {
        return dateTimeFinal;
    }

    public void setDateTimeFinal(LocalDateTime dateTimeFinal) {
        this.dateTimeFinal = dateTimeFinal;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getPeriodicidade() {
        return periodicidade;
    }

    public void setPeriodicidade(int periodicidade) {
        this.periodicidade = periodicidade;
    }

    @Override
    public String toString() {
        return "Evento{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", dateTimeInicial=" + dateTimeInicial +
                ", dateTimeFinal=" + dateTimeFinal +
                ", descricao='" + descricao + '\'' +
                ", periodicidade=" + periodicidade +
                ", seguidores=" + seguidores +
                '}';
    }
}

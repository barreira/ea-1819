package com.gestaoespacos.app.model;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

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
    //private Evento evento;
    private Espaco espaco;

    public Evento() {
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

    public Espaco getEspaco() {
        return espaco;
    }

    public void setEspaco(Espaco espaco) {
        this.espaco = espaco;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Evento evento1 = (Evento) o;
        return getId() == evento1.getId() &&
                getPeriodicidade() == evento1.getPeriodicidade() &&
                getNome().equals(evento1.getNome()) &&
                getDateTimeInicial().equals(evento1.getDateTimeInicial()) &&
                getDateTimeFinal().equals(evento1.getDateTimeFinal()) &&
                getDescricao().equals(evento1.getDescricao()) &&
                getSeguidores().equals(evento1.getSeguidores()) &&
                getUtilizadorResponsavel().equals(evento1.getUtilizadorResponsavel()) &&
                getEspaco().equals(evento1.getEspaco());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNome(), getDateTimeInicial(), getDateTimeFinal(), getDescricao(), getPeriodicidade(), getSeguidores(), getUtilizadorResponsavel(), getEspaco());
    }
}

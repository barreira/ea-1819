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

    private boolean repeteDia;
    private boolean repeteSemana;
    private boolean repeteMes;
    private boolean repeteAno;

    @OneToMany
    @JoinColumn(name = "evento_id")
    private List<Utilizador> seguidores;

    @OneToOne
    private UtilizadorCPDR utilizadorResponsavel;


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

    public UtilizadorCPDR getUtilizadorResponsavel() {
        return utilizadorResponsavel;
    }

    public void setUtilizadorResponsavel(UtilizadorCPDR utilizadorResponsavel) {
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

    public boolean isRepeteDia() {
        return repeteDia;
    }

    public void setRepeteDia(boolean repeteDia) {
        this.repeteDia = repeteDia;
    }

    public boolean isRepeteSemana() {
        return repeteSemana;
    }

    public void setRepeteSemana(boolean repeteSemana) {
        this.repeteSemana = repeteSemana;
    }

    public boolean isRepeteMes() {
        return repeteMes;
    }

    public void setRepeteMes(boolean repeteMes) {
        this.repeteMes = repeteMes;
    }

    public boolean isRepeteAno() {
        return repeteAno;
    }

    public void setRepeteAno(boolean repeteAno) {
        this.repeteAno = repeteAno;
    }

    @Override
    public String toString() {
        return "Evento{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", dateTimeInicial=" + dateTimeInicial +
                ", dateTimeFinal=" + dateTimeFinal +
                ", descricao='" + descricao + '\'' +
                ", repeteDia=" + repeteDia +
                ", repeteSemana=" + repeteSemana +
                ", repeteMes=" + repeteMes +
                ", repeteAno=" + repeteAno +
                ", seguidores=" + seguidores +
                '}';
    }
}

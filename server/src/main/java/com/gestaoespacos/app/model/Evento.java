package com.gestaoespacos.app.model;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String nome;

    private LocalDateTime dateTimeInicial;
    private LocalDateTime dateTimeFinal;
    private LocalDateTime limite;

    private String descricao;

    private int periodicidade;

    @ManyToMany(mappedBy = "eventosASeguir", fetch = FetchType.EAGER)
    //@JoinColumn(name = "evento_id")
    private Set<Utilizador> seguidores;

    @OneToOne
    //private Responsavel utilizadorResponsavel;
    //Solução provisória. Responsavel é uma Interface, pelo que não pode ser persistida
    //é necessário garantir que este Ator é instância de GestorEspacos ou UtilizadorCPDR
    private Ator utilizadorResponsavel;


    // TODO: Duvida, nao podera haver varios espacos por evento?!
    @OneToOne
    private Espaco espaco;

    public Evento() {
    }

    public Evento(String nome, String desc, LocalDateTime inicio, LocalDateTime fim, int p, LocalDateTime limite){
        this.nome = nome;
        this.descricao = desc;
        this.dateTimeInicial = inicio;
        this.dateTimeFinal = fim;
        this.periodicidade = p;
        this.limite = limite;
        this.seguidores = new HashSet<>();
        this.espaco = null;
    }

    /*public Responsavel getUtilizadorResponsavel() {
        return utilizadorResponsavel;
    }

    public void setUtilizadorResponsavel(Responsavel utilizadorResponsavel) {
        this.utilizadorResponsavel = utilizadorResponsavel;
    }*/
    public Ator getUtilizadorResponsavel() {
        return utilizadorResponsavel;
    }

    public void setUtilizadorResponsavel(Ator utilizadorResponsavel) {
        this.utilizadorResponsavel = utilizadorResponsavel;
    }

    public Set<Utilizador> getSeguidores() {
        return seguidores;
    }

    public void setSeguidores(Set<Utilizador> seguidores) {
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

    public LocalDateTime getLimite() {
        return limite;
    }

    public void setLimite(LocalDateTime limite) {
        this.limite = limite;
    }

    public Espaco getEspaco() {
        return espaco;
    }

    public void setEspaco(Espaco espaco) {
        this.espaco = espaco;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Evento evento = (Evento) o;
        return getId() == evento.getId() &&
                getPeriodicidade() == evento.getPeriodicidade() &&
                Objects.equals(getNome(), evento.getNome()) &&
                Objects.equals(getDateTimeInicial(), evento.getDateTimeInicial()) &&
                Objects.equals(getDateTimeFinal(), evento.getDateTimeFinal()) &&
                Objects.equals(getLimite(), evento.getLimite()) &&
                Objects.equals(getDescricao(), evento.getDescricao()) &&
                Objects.equals(getSeguidores(), evento.getSeguidores()) &&
                Objects.equals(getUtilizadorResponsavel(), evento.getUtilizadorResponsavel()) &&
                Objects.equals(getEspaco(), evento.getEspaco());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNome(), getDateTimeInicial(), getDateTimeFinal(), getLimite(), getDescricao(), getPeriodicidade(), getSeguidores(), getUtilizadorResponsavel(), getEspaco());
    }

    @Override
    public String toString() {
        return "Evento{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", dateTimeInicial=" + dateTimeInicial +
                ", dateTimeFinal=" + dateTimeFinal +
                ", limite=" + limite +
                ", descricao='" + descricao + '\'' +
                ", periodicidade=" + periodicidade +
                ", seguidores=" + seguidores +
                ", utilizadorResponsavel=" + utilizadorResponsavel +
                ", espaco=" + espaco +
                '}';
    }
}

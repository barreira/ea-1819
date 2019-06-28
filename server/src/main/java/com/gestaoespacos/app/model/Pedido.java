package com.gestaoespacos.app.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
        name="tipoPedido",
        discriminatorType = DiscriminatorType.CHAR
)
@DiscriminatorValue("P")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public abstract class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private boolean aceite;
    private boolean atendido;

    //@OneToOne
    //private Evento evento;
    //Detalhes do novo evento/atualização do evento
    private String nome;
    private LocalDateTime dateTimeInicial;
    private LocalDateTime dateTimeFinal;
    private String descricao;
    private int periodicidade;
    private LocalDateTime limite;
    @OneToOne
    private Espaco esp;

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
        return esp;
    }

    public void setEspaco(Espaco esp) {
        this.esp = esp;
    }

    public Pedido() {
    }

    public Pedido(long id, boolean aceite) {
        this.id = id;
        this.aceite = aceite;
    }

    public Pedido(String nome, LocalDateTime inicio, LocalDateTime fim, String descricao, int periodo, LocalDateTime limite, Espaco e){
        this.aceite = false;
        this.atendido = false;
        this.nome = nome;
        this.descricao = descricao;
        this.dateTimeInicial = inicio;
        this.dateTimeFinal = fim;
        this.periodicidade = periodo;
        this.limite = limite;
        this.esp = e;
    }

    /*public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }*/

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isAceite() {
        return aceite;
    }

    public void setAceite(boolean aceite) {
        this.aceite = aceite;
    }

    public boolean isAtendido() { return atendido; }

    public void setAtendido(boolean atendido) { this.atendido = atendido; }

    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", aceite=" + aceite +
                ", atendido=" + atendido +
                ", nome='" + nome + '\'' +
                ", dateTimeInicial=" + dateTimeInicial +
                ", dateTimeFinal=" + dateTimeFinal +
                ", descricao='" + descricao + '\'' +
                ", periodicidade=" + periodicidade +
                ", limite=" + limite +
                ", esp=" + esp +
                '}';
    }
}

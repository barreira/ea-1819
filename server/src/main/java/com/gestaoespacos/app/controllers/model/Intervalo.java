package com.gestaoespacos.app.controllers.model;

import java.time.LocalDate;

public class Intervalo {

    private LocalDate inicio;
    private LocalDate fim;
    private Long espaco;

    public Intervalo(){}

    public Intervalo(LocalDate inicio, LocalDate fim){
        this.inicio = inicio;
        this.fim = fim;
        this.espaco = null;
    }

    public Intervalo(LocalDate inicio, LocalDate fim, Long espaco){
        this.inicio = inicio;
        this.fim = fim;
        this.espaco = espaco;
    }

    public LocalDate getInicio() {
        return inicio;
    }

    public void setInicio(LocalDate inicio) {
        this.inicio = inicio;
    }

    public LocalDate getFim() {
        return fim;
    }

    public void setFim(LocalDate fim) {
        this.fim = fim;
    }

    public Long getEspaco() {
        return espaco;
    }

    public void setEspaco(Long espaco) {
        this.espaco = espaco;
    }

    @Override
    public String toString() {
        return "Intervalo{" +
                "inicio=" + inicio +
                ", fim=" + fim +
                ", espaco=" + espaco +
                '}';
    }
}

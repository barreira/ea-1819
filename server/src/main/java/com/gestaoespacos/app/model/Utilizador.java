package com.gestaoespacos.app.model;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

import javax.persistence.*;

@Entity
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Utilizador extends Ator{


    @Column(unique = true)
    private String email;

    private String nome;

    private Date registrationDate;

    @OneToMany
    @JoinColumn(name = "utilizador_id")
    private List<Notificacao> notificacoes;

    @OneToMany
    @JoinColumn(name = "utilizador_id")
    private Set<Evento> eventosASeguir;


    public Utilizador() {
        super();
    }

    public Utilizador(String username) {
        super(username);
    }

    public Utilizador(String username, String email, String password) {
        super(username, password);
        this.email = email;
        this.registrationDate = Calendar.getInstance().getTime();
    }

    public Set<Evento> getEventosASeguir() { return eventosASeguir;
    }

    public void setEventosASeguir(Set<Evento> eventosASeguir) {
        this.eventosASeguir = eventosASeguir;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public List<Notificacao> getNotificacoes() {
        return notificacoes;
    }

    public void setNotificacoes(List<Notificacao> notificacoes) {
        this.notificacoes = notificacoes;
    }

    @Override
    public String toString() {
        return "Utilizador{" +
                "id=" + this.getId() +
                ", username='" + getUsername() + '\'' +
                ", email='" + email + '\'' +
                ", password='" + getPassword() + '\'' +
                ", registrationDate=" + registrationDate +
                ", notificacoes=" + notificacoes +
                '}';
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Evento follow(Evento e){
        eventosASeguir.add(e);

        return e;
    }

    public Evento unfollow(Evento e){
        eventosASeguir.remove(e);

        return e;
    }
}

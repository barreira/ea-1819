package com.gestaoespacos.app.model;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

@Entity
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Utilizador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String email;

    private String password;

    private Date registrationDate;

    public void setId(long id) {
        this.id = id;
    }

    @OneToMany
    @JoinColumn(name = "utilizador_id")
    private List<Notificacao> notificacoes;

    @OneToMany
    @JoinColumn(name = "utilizador_id")
    private List<Evento> eventosASeguir;


    public Utilizador() {
    }

    public Utilizador(String username) {
        this.username = username;
    }

    public Utilizador(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.registrationDate = Calendar.getInstance().getTime();

        MessageDigest digest = null;

        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
        String encoded = Base64.getEncoder().encodeToString(hash);
        this.password = password;
    }

    public List<Evento> getEventosASeguir() { return eventosASeguir;
    }

    public void setEventosASeguir(List<Evento> eventosASeguir) {
        this.eventosASeguir = eventosASeguir;
    }
    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", registrationDate=" + registrationDate +
                ", notificacoes=" + notificacoes +
                '}';
    }
}

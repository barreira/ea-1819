package com.gestaoespacos.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Utilizador.class, name = "utilizador"),
        @JsonSubTypes.Type(value = UtilizadorCPDR.class, name = "utilizadorcpdr"),
        @JsonSubTypes.Type(value = Administrador.class, name = "administrador"),
        @JsonSubTypes.Type(value = GestorEspacos.class, name = "gestorespacos")
})
public abstract class Ator implements UserDetails {

    @Id
    @GeneratedValue(strategy =  GenerationType.SEQUENCE)
    //@GeneratedValue(strategy =  GenerationType.IDENTITY)
    //@GeneratedValue(strategy = GenerationType.TABLE)
    private long id;

    @Column(unique = true)
    private String username;
    private String password;

    public Ator(){}

    public Ator(String username) {
        this.username = username;
    }

    public Ator(String username, String password) {
        this.username = username;
        this.password = password; //UtilsGHE.encode(password);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Ator{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    ///////Security/////////
    @Override
    @JsonIgnore
    public Collection<GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }

}

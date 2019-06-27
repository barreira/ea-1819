package com.gestaoespacos.app.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Ator implements UserDetails {

    @Id
    //@GeneratedValue(strategy =  GenerationType.IDENTITY)
    @GeneratedValue(strategy = GenerationType.TABLE)
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
        this.password = UtilsGHE.encode(password);
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
    public Collection<GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}

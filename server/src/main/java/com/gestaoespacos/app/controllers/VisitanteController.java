package com.gestaoespacos.app.controllers;

import com.gestaoespacos.app.model.*;
import com.gestaoespacos.app.security.UserAuthenticationService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@RestController
@RequestMapping("/public/users")
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(access = PACKAGE)
final class VisitanteController {
    @NonNull
    private UserAuthenticationService authentication;

    @PostMapping("/register")
    String register(
            @RequestParam("username") final String username,
            @RequestParam("email") final String email,
            @RequestParam("password") final String password,
            @RequestParam("nome") final String nome) {
        GHE.registarUtilizador(new Utilizador(username, password, email, nome));

        return login(username, password);
    }

    @PostMapping("/login")
    String login(
            @RequestParam("username") final String username,
            @RequestParam("password") final String password) {
        return authentication
                //.login(username, UtilsGHE.encode(password))
                .login(username, password)
                .orElseThrow(() -> new RuntimeException("invalid login and/or password"));
    }

    @GetMapping("/evento")
    public Evento consultarEvento(@RequestParam("nome") String nome){
        try{
            return GHE.consultarEvento(nome);
        }catch(EventoDoesNotExistException e){ System.out.println(e);}

        return null;
    }

    @GetMapping("/horario")
    public Horario consultarHorario(@RequestParam("espaco") String espaco){
        try{
            return GHE.consultarHorario(espaco);
        }catch(EspacoDoesNotExistException e){ System.out.println(e);}

        return null;
    }

    @GetMapping("/eventos")
    public Map<LocalDate, Set<Evento>> eventosEntreDatas(
            @RequestParam("inicio")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                    LocalDate inicio,
            @RequestParam("fim")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                    LocalDate fim,
            @RequestParam(name = "espaco", required = false) Long id_espaco){
        try{
            return id_espaco == null ? GHE.eventosEntreDatas(inicio, fim) : GHE.eventosEntreDatas(inicio, fim, id_espaco);
        }catch(IdNotFoundException e){ System.out.println(e);}

        return null;
    }
}

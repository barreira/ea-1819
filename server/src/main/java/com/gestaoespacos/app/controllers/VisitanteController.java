package com.gestaoespacos.app.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
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
    private ObjectMapper mapper = new ObjectMapper();

    @PostMapping("/register")
    public String register(@RequestBody ObjectNode user) {
        String username = user.get("username").asText();
        String password = user.get("password").asText();
        String email = user.get("email").asText();
        String nome = user.get("nome").asText();

        GHE.registarUtilizador(new Utilizador(username, password, email, nome));

        return login(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody ObjectNode credenciais) {
        String username = credenciais.get("username").asText();
        String password = credenciais.get("password").asText();

        return authentication
                //.login(username, UtilsGHE.encode(password))
                .login(username, password)
                .orElseThrow(() -> new RuntimeException("invalid login and/or password"));
    }

    @GetMapping("/evento")
    public Evento consultarEvento(@RequestBody ObjectNode nome){
        try{
            return GHE.consultarEvento(nome.get("nome").asText());
        }catch(EventoDoesNotExistException e){ System.out.println(e);}

        return null;
    }

    @GetMapping("/horario")
    public Horario consultarHorario(@RequestBody ObjectNode espaco){
        try{
            return GHE.consultarHorario(espaco.get("espaco").asText());
        }catch(EspacoDoesNotExistException e){ System.out.println(e);}

        return null;
    }

    @GetMapping("/eventos")
    public Map<LocalDate, Set<Evento>> eventosEntreDatas(@RequestBody ObjectNode intervalo){
        try{
            LocalDate inicio =  mapper.readValue(intervalo.get("inicio").asText(), LocalDate.class);
            LocalDate fim =  mapper.readValue(intervalo.get("fim").asText(), LocalDate.class);
            Long id_espaco = intervalo.get("espaco").asLong();

            return id_espaco == null ? GHE.eventosEntreDatas(inicio, fim) : GHE.eventosEntreDatas(inicio, fim, id_espaco);
        }catch(IdNotFoundException e){ System.out.println(e);}
         catch(Exception e){}

        return null;
    }
}

    /*@PostMapping("/register")
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
            @RequestParam final String username,
            @RequestParam final String password) {
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
    }*/

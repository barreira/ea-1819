package com.gestaoespacos.app.controllers;


import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gestaoespacos.app.controllers.model.Intervalo;
import com.gestaoespacos.app.model.*;
import com.gestaoespacos.app.repositories.AtorRepository;
//import com.gestaoespacos.app.security.UserAuthenticationService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    @Autowired
    private AtorRepository atorRepository;

    @Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/login")
    public String login(@RequestBody Utilizador utilizador) {
        Optional<Ator> currentAtor = atorRepository.findByUsername(utilizador.getUsername());

        if(!currentAtor.isPresent()){
            return null;
        }

        Ator fetchAtor = currentAtor.get();

        System.out.println("Fetch Ator");
        System.out.println(fetchAtor);

        System.out.println("Utilizador: " );
        System.out.println(utilizador );
        System.out.println(bCryptPasswordEncoder.encode(utilizador.getPassword()));

        if(fetchAtor != null) {

            if (bCryptPasswordEncoder.matches(utilizador.getPassword(), fetchAtor.getPassword())) {

                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                System.out.println(auth.getAuthorities());
                System.out.println(auth.toString());

                return "Login realizado com sucesso!";
            }
        }

        return null;
    }

    @GetMapping("/test")
    public String test(){
        return "Just a Test!";
    }

    @PostMapping("/register")
    public String register(@RequestBody Utilizador utilizador) {
        System.out.println("Registering " + utilizador.toString());

        Optional<Ator> findByUsername = atorRepository.findByUsername(utilizador.getUsername());

        if(findByUsername.isPresent()){
           return "Username already exists" ;
        }

        Ator newAtor = new Utilizador(utilizador.getUsername(),
                bCryptPasswordEncoder.encode(utilizador.getPassword()));

        atorRepository.save(newAtor);

        return login(utilizador);
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
    public Map<LocalDate, Set<Evento>> eventosEntreDatas(@RequestBody Intervalo intervalo){
        try{
            LocalDate inicio =  intervalo.getInicio();
            LocalDate fim =  intervalo.getFim();
            Long espaco = intervalo.getEspaco();

            return espaco == null ? GHE.eventosEntreDatas(inicio, fim) : GHE.eventosEntreDatas(inicio, fim,  espaco);
        }catch(IdNotFoundException e){ System.out.println(e);}
         catch(Exception e){ System.out.println(e); }

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

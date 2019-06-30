package com.gestaoespacos.app.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gestaoespacos.app.controllers.model.Intervalo;
import com.gestaoespacos.app.model.*;
import com.gestaoespacos.app.repositories.*;
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
    private UserRepository userRepository;
    @Autowired
    private UtilizadorCPDRRepository usercpdrRepository;
    @Autowired
    private GestorEspacosRepository gestorRepository;
    @Autowired
    private AdministradorRepository adminRepository;
    @Autowired
    private EspacoComumRepository ecr;

    @Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/login")
    public String login(@RequestBody ObjectNode ator) {
        String username = ator.get("username").asText();
        String password = ator.get("password").asText();

        Optional<Ator> currentAtor = atorRepository.findByUsername(username);

        if(!currentAtor.isPresent()){
            return null;
        }

        Ator fetchAtor = currentAtor.get();

        System.out.println("Fetch Ator");
        System.out.println(fetchAtor);

        System.out.println("Utilizador: " );
        System.out.println(ator.toString());
        System.out.println(bCryptPasswordEncoder.encode(password));

        if(fetchAtor != null) {

            if (bCryptPasswordEncoder.matches(password, fetchAtor.getPassword())) {
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

        Utilizador newAtor = new Utilizador(utilizador.getUsername(), bCryptPasswordEncoder.encode(utilizador.getPassword()),
                                            utilizador.getEmail(), utilizador.getNome());

        GHE.registarUtilizador(newAtor);

       // ObjectNode u = new ObjectMapper().createObjectNode()
        //                                 .put("username", utilizador.getUsername())
         //                                .put("password", utilizador.getPassword())
           //                              .put("type", "utilizador");
        return "Sucessfully registered!";
    }

    @PostMapping("/registerucpdr")
    public String register(@RequestBody UtilizadorCPDR utilizador) {
        System.out.println("Registering " + utilizador.toString());

        Optional<Ator> findByUsername = atorRepository.findByUsername(utilizador.getUsername());

        if(findByUsername.isPresent()){
            return "Username already exists" ;
        }

        UtilizadorCPDR newAtor = new UtilizadorCPDR(utilizador.getUsername(), bCryptPasswordEncoder.encode(utilizador.getPassword()),
                                                    utilizador.getEmail(), utilizador.getNome());

        usercpdrRepository.save(newAtor);

        ObjectNode u = new ObjectMapper().createObjectNode()
                                         .put("username", utilizador.getUsername())
                                         .put("password", utilizador.getPassword())
                                         .put("type", "utilizadorcpdr");

        return login(u);
    }

    @PostMapping("/evento")
    public Evento consultarEvento(@RequestBody ObjectNode nome){
        try{
            return GHE.consultarEvento(nome.get("nome").asText());
        }catch(EventoDoesNotExistException e){ System.out.println(e);}

        return null;
    }

    @PostMapping("/horario")
    public Horario consultarHorario(@RequestBody ObjectNode espaco){
        try{
            return GHE.consultarHorario(espaco.get("espaco").asText());
        }catch(EspacoDoesNotExistException e){ System.out.println(e);}

        return null;
    }

    @PostMapping("/eventos")
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

    @GetMapping("/ecs/view/{id_ec}")
    public Optional<EspacoComum> viewEC(@PathVariable long id_ec){
        return ecr.findById(id_ec);
    }

    @GetMapping("/ecs/viewAll")
    public List<EspacoComum> viewAllEC(){
        return ecr.findAll();
    }

    @PostMapping("/exists/user")
    public boolean existsUtilizador(@RequestBody ObjectNode username){
        return userRepository.findByUsername(username.get("username").asText()).isPresent();
    }

    @PostMapping("/exists/usercpdr")
    public boolean existsUtilizadorCPDR(@RequestBody ObjectNode username){
        return usercpdrRepository.findByUsername(username.get("username").asText()).isPresent();
    }

    @PostMapping("/exists/gestor")
    public boolean existsGestor(@RequestBody ObjectNode username){
        return gestorRepository.findByUsername(username.get("username").asText()).isPresent();
    }

    @PostMapping("/exists/admin")
    public boolean existsAdmin(@RequestBody ObjectNode username){
        return adminRepository.findByUsername(username.get("username").asText()).isPresent();
    }


    /**
     * TODO: CUIDADO!! fazer só uma vez !!
     * @return
     */
    @PostMapping("/encode")
    public String encodePW(){
        atorRepository.findAll().forEach(a ->{
            a.setPassword(bCryptPasswordEncoder.encode(a.getPassword()));
            atorRepository.save(a);
        });

        return "Done";
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

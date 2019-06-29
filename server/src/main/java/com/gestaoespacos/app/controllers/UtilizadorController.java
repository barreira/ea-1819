package com.gestaoespacos.app.controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gestaoespacos.app.model.*;
import com.gestaoespacos.app.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UtilizadorController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public @ResponseBody
    String teste() {
        return "Este é um teste.";
    }

    @PostMapping("/add")
    public Utilizador addUser(@RequestBody Utilizador utilizador) {
        return userRepository.save(utilizador);
    }

    @PostMapping("/edit")
    public void editUser(@RequestParam long id) {
        Optional<Utilizador> u = userRepository.findById(id);

        if (u.isPresent()) {
            Utilizador users = u.get();
            userRepository.save(users);
        }
    }

    @PostMapping("/delete/{id}")
    public void deleteUser(@PathVariable long id) {
        userRepository.deleteById(id);
    }

    @GetMapping("/view/{id}")
    public Optional<Utilizador> viewUserById(@PathVariable long id) {
        return userRepository.findById(id);
    }

    @PostMapping("/follow")
    public String follow(@RequestBody ObjectNode f){

        try{
            long id_user = f.get("id_user").asLong();
            long id_evt = f.get("id_evt").asLong();
            Evento e = GHE.follow(id_user, id_evt);
            return "A seguir " + e.getNome() + " com sucesso.";
        }catch(IdNotFoundException e){ System.out.println(e);}

        return "Não foi possível seguir o evento especificado, para o utilizador fornecido.";
    }

    @PostMapping("/unfollow")
    public String unfollow(@RequestBody ObjectNode unf){

        try{
            long id_user = unf.get("id_user").asLong();
            long id_evt = unf.get("id_evt").asLong();
            Evento e = GHE.unfollow(id_user, id_evt);
            return "Deixou de seguir " + e.getNome() + " com sucesso.";
        }catch(IdNotFoundException e){ System.out.println(e);}

        return "Não foi possível deixar de seguir o evento especificado, para o utilizador fornecido.";
    }

    @GetMapping("/following/{id}")
    public Set<Evento> getFollowing(@PathVariable long id){
        try{
            return GHE.getFollowing(id);
        }catch(IdNotFoundException e){ System.out.println(e);}

        return null;
    }

    @GetMapping("/notificacoes/{id}")
    public List<Notificacao> getNotificacoes(@PathVariable long id){
        try{
            return GHE.getNotificacoes(id);
        }catch(IdNotFoundException e){ System.out.println(e);}

        return null;
    }

    /**
     * URL: https://accounts.google.com/o/oauth2/auth?client_id=810326640331-eth52d1a0ocejiero78e6edosfbcf9vf.apps.googleusercontent.com&redirect_uri=urn:ietf:wg:oauth:2.0:oob&response_type=code&scope=https://www.googleapis.com/auth/calendar
     * email: utilizadorghe@gmail.com
     * password: 1234.567
     */
    @PostMapping("/calendar")
    public boolean syncCalendar(@RequestBody ObjectNode code){
        try{
            return GHE.syncCalendar(code.get("id").asLong(), code.get("code").asText());
        }catch(IdNotFoundException e){ System.out.println(e);}

        return false;
    }

}

/*
 @PostMapping("/unfollow")
 public String unfollow(
 @RequestParam("id_user") final long id_user,
 @RequestParam("id_evt") final long id_evt){

 try{
 Evento e = GHE.unfollow(id_user, id_evt);
 return "Deixou de seguir " + e.getNome() + " com sucesso.";
 }catch(IdNotFoundException e){ System.out.println(e);}

 return "Não foi possível deixar de seguir o evento especificado, para o utilizador fornecido.";
 }

 @GetMapping("/following/{id}")
 public Set<Evento> getFollowing(@PathVariable long id){
 try{
 return GHE.getFollowing(id);
 }catch(IdNotFoundException e){ System.out.println(e);}

 return null;
 }

 /*@GetMapping("/notificacoes/{id}")
 public List<Notificacao> getNotificacoes(@PathVariable long id){
 try{
 return GHE.getNotificacoes(id);
 }catch(IdNotFoundException e){ System.out.println(e);}

 return null;
 }

@GetMapping("/notificacoes")
public List<Notificacao> getNotificacoes(@RequestParam("id") long id){
    try{
        return GHE.getNotificacoes(id);
    }catch(IdNotFoundException e){ System.out.println(e);}

    return null;
}*/
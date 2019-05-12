package com.gestaoespacos.app.controllers;

import com.gestaoespacos.app.model.Utilizador;
import com.gestaoespacos.app.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

}

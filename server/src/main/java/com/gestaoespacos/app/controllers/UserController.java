package com.gestaoespacos.app.controllers;

import com.gestaoespacos.app.model.User;
import com.gestaoespacos.app.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public @ResponseBody
    String teste() {
        return "Este é um teste.";
    }

    @PostMapping("/add")
    public User addUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    @PostMapping("/edit")
    public void editUser(@RequestParam long id) {
        Optional<User> u = userRepository.findById(id);

        if (u.isPresent()) {
            User users = u.get();
            userRepository.save(users);
        }
    }

    @PostMapping("/delete/{id}")
    public void deleteUser(@PathVariable long id) {
        userRepository.deleteById(id);
    }

    @GetMapping("/view/{id}")
    public Optional<User> viewUserById(@PathVariable long id) {
        return userRepository.findById(id);
    }

}

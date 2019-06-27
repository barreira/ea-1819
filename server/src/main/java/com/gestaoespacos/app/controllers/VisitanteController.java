package com.gestaoespacos.app.controllers;

import com.gestaoespacos.app.model.Utilizador;
import com.gestaoespacos.app.model.UtilsGHE;
import com.gestaoespacos.app.repositories.UserRepository;
import com.gestaoespacos.app.security.UserAuthenticationService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@RestController
@RequestMapping("/public/users")
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(access = PACKAGE)
final class VisitanteController {
    @NonNull
    private UserAuthenticationService authentication;
    @NonNull
    private UserRepository users;

    @PostMapping("/register")
    String register(
            @RequestParam("username") final String username,
            @RequestParam("email") final String email,
            @RequestParam("password") final String password) {
        users.save(new Utilizador(username, email, password));

        return login(username, password);
    }

    @PostMapping("/login")
    String login(
            @RequestParam("username") final String username,
            @RequestParam("password") final String password) {
        return authentication
                .login(username, UtilsGHE.encode(password))
                .orElseThrow(() -> new RuntimeException("invalid login and/or password"));
    }
}

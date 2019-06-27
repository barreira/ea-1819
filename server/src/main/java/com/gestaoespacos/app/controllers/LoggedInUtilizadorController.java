package com.gestaoespacos.app.controllers;

import com.gestaoespacos.app.model.Ator;
import com.gestaoespacos.app.security.UserAuthenticationService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@RestController
@RequestMapping("/users")
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(access = PACKAGE)
final class LoggedInUtilizadorController {
    @NonNull
    private UserAuthenticationService authentication;

    @GetMapping("/current")
    Ator getCurrent(@AuthenticationPrincipal final Ator user) {
        return user;
    }

    @GetMapping("/logout")
    boolean logout(@AuthenticationPrincipal final Ator  user) {
        authentication.logout(user);
        return true;
    }
}
package com.gestaoespacos.app.security;

import com.gestaoespacos.app.model.Ator;
import com.gestaoespacos.app.repositories.AtorRepository;
import com.google.common.collect.ImmutableMap;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.*;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;


@Service
@AllArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
final class TokenAuthenticationService implements UserAuthenticationService {
    @NonNull
    private TokenService tokens;
    @NonNull
    private AtorRepository users;


    @Override
    public Optional<String> login(final String username, final String password) {
        return  users
                     .findByUsername(username)
                     .filter(user -> Objects.equals(password, user.getPassword()))
                     .map(user -> tokens.expiring(ImmutableMap.of("username", username)));

    }

    @Override
    public Optional<Ator> findByToken(final String token) {
        return Optional
                .of(tokens.verify(token))
                .map(map -> map.get("username"))
                .flatMap(users::findByUsername);
    }

    @Override
    public void logout(final Ator user) {
        // Nothing to doy
    }
}

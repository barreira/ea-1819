package com.gestaoespacos.app.security;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.gestaoespacos.app.model.Ator;
import com.gestaoespacos.app.model.Utilizador;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import static com.gestaoespacos.app.security.SecurityConstants.EXPIRATION_TIME;
import static com.gestaoespacos.app.security.SecurityConstants.SECRET;
import static com.gestaoespacos.app.security.SecurityConstants.HEADER_STRING;
import static com.gestaoespacos.app.security.SecurityConstants.TOKEN_PREFIX;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/public/users/login", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
            throws AuthenticationException {

        System.out.println("Attempting authentication");
        try {
            Ator creds = new ObjectMapper().readValue(req.getInputStream(), Ator.class);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(creds.getUsername(),
                    creds.getPassword(), new ArrayList<>()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {


        System.out.println("Valid authentication");

        String role = auth.getAuthorities().toString().replace("[", "").replace("]", "");
        System.out.println(role);

        String token = Jwts.builder().setSubject(((User) auth.getPrincipal()).getUsername())
                .claim("username", auth.getName())
                .claim("role", role)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET.getBytes()).compact();

        System.out.println("Token");
        System.out.println(token);

        res.getWriter().write("{\"token\" : \"" + token + "\"}");
        res.addHeader("Content-Type", "application/json");
        res.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
    }
}
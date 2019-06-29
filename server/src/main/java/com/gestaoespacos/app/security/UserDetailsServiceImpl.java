package com.gestaoespacos.app.security;

import com.gestaoespacos.app.model.Ator;
import com.gestaoespacos.app.repositories.AtorRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.util.Collections.emptyList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private AtorRepository atorRepository;
    private String userId;
    
    public UserDetailsServiceImpl(AtorRepository atorRepository) {
        this.atorRepository = atorRepository;
    }
    
    

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Ator> applicationUser = atorRepository.findByUsername(username);

        Ator appUser = null;
        if(applicationUser.isPresent()){
           appUser = applicationUser.get();
        } else {
            throw new UsernameNotFoundException(username);
        }

        return new User(appUser.getUsername(), appUser.getPassword(), emptyList());
    }
}
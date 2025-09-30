package com.example.szamlakezelo.config;


import com.example.szamlakezelo.model.User;
import com.example.szamlakezelo.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Felhasználó nem található"));

        System.out.println("Jogosultságok: " +
                user.getRole().stream()
                        .map(role -> role.getName().name())
                        .toList()
        );


        Set<GrantedAuthority> authorities = new HashSet<>();
        user.getRole().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName().name())));


        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authorities
        );
    }
}


package com.example.szamlakezelo.service;


import com.example.szamlakezelo.model.Role;
import com.example.szamlakezelo.model.Role_enum;
import com.example.szamlakezelo.model.User;
import com.example.szamlakezelo.repo.RoleRepository;
import com.example.szamlakezelo.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;


@Service
public class UserService implements UserDetailsService {
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(8);

    private UserRepository userRepository;
    private RoleRepository roleRepository;


    @Autowired
    public void  setRoleRepository(RoleRepository roleRepository) {this.roleRepository = roleRepository;}

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }



    public void register(User user) {
        User existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser == null) {
            user.setPassword(encoder.encode(user.getPassword()));
            userRepository.save(user);
        }else {
            throw new IllegalArgumentException("Ez a felhasználónév már létezik!");
        }

    }


    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Hibás felhsználónév vagy jelszó");
        }
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .roles("USER")
                .build();
    }

}

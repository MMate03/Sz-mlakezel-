package com.example.szamlakezelo.service;


import com.example.szamlakezelo.model.Role;
import com.example.szamlakezelo.model.User;
import com.example.szamlakezelo.repo.RoleRepository;
import com.example.szamlakezelo.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;


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
        Optional<User> existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser.isEmpty()) {
            user.setPassword(encoder.encode(user.getPassword()));
            userRepository.save(user);
        } else {
            throw new IllegalArgumentException("Ez a felhasználónév már létezik!");
        }
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void setLoginDate(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Felhasználó nem található"));
        user.setLoginDate(LocalDate.now());
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Hibás felhasználó név vagy jelszó"));
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .roles("USER")
                .build();
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public void updateUserRoles(Long id, List<String> roles) {
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            Set<Role> newRoles = roleRepository.findByNameIn(roles);

            user.setRoles(newRoles);
            userRepository.save(user);
        }
    }
}

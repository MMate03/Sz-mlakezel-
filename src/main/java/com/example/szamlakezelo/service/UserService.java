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
import java.util.Objects;
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
        if (user.getUsername() == null || user.getUsername().length() < 5) {
            throw new IllegalArgumentException("A felhasználónévnek legalább 5 karakter hosszúnak kell lennie!");
        }
        if (user.getName() == null || user.getName().length() < 5) {
            throw new IllegalArgumentException("A névnek legalább 5 karakter hosszúnak kell lennie!");
        }

        String password = user.getPassword();
        if (!isValidPassword(password)) {
            throw new IllegalArgumentException("A jelszónak legalább 10 karakter hosszúnak kell lennie, tartalmaznia kell legalább 1 nagybetűt és 1 számot!");
        }
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

    private boolean isValidPassword(String password) {
        if (password == null || password.length() < 10) {
            return false;
        }
        boolean hasUppercase = password.chars().anyMatch(Character::isUpperCase);
        boolean hasDigit = password.chars().anyMatch(Character::isDigit);
        return hasUppercase && hasDigit;
    }

    public List<String> findCustomerNames(String query) {
        return userRepository.findAll().stream()
                .map(User::getName)
                .filter(Objects::nonNull)
                .filter(name -> name.toLowerCase().contains(query.toLowerCase()))
                .distinct()
                .toList();
    }
}

package com.example.szamlakezelo.config;

import com.example.szamlakezelo.model.Role;
import com.example.szamlakezelo.model.Role_enum;
import com.example.szamlakezelo.repo.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    public DataInitializer(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) {
        if (roleRepository.findByName(Role_enum.ROLE_USER) == null) {
            Role role = new Role();
            role.setName(Role_enum.ROLE_USER);
            roleRepository.save(role);
        }
        if (roleRepository.findByName(Role_enum.ROLE_ADMIN) == null) {
            Role role = new Role();
            role.setName(Role_enum.ROLE_ADMIN);
            roleRepository.save(role);
        }
        if (roleRepository.findByName(Role_enum.ROLE_ACCOUNTANT) == null) {
            Role role = new Role();
            role.setName(Role_enum.ROLE_ACCOUNTANT);
            roleRepository.save(role);
        }
    }
}


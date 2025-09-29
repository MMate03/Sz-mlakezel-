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
            role.setDescription("Általános user, csak a számlák listáját látja, és a számlákat külön tudja megnyitni.");
            roleRepository.save(role);
        }
        if (roleRepository.findByName(Role_enum.ROLE_ADMIN) == null) {
            Role role = new Role();
            role.setName(Role_enum.ROLE_ADMIN);
            role.setDescription("Látja az adminisztrációs oldalt, valamint az összes többi másik aloldalt is.");
            roleRepository.save(role);
        }
        if (roleRepository.findByName(Role_enum.ROLE_ACCOUNTANT) == null) {
            Role role = new Role();
            role.setName(Role_enum.ROLE_ACCOUNTANT);
            role.setDescription("Tud számlákat létrehozni, látja a számlák listáját is, külön a számlákat is meg tudja tekinteni, de az adminisztrációs oldalt nem.");
            roleRepository.save(role);
        }
    }
}


package com.example.szamlakezelo.config;

import com.example.szamlakezelo.model.Invoice;
import com.example.szamlakezelo.model.Role_enum;
import com.example.szamlakezelo.model.User;
import com.example.szamlakezelo.repo.InvoiceRepository;
import com.example.szamlakezelo.repo.RoleRepository;
import com.example.szamlakezelo.repo.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

@Component
@Order(2)
public class TestDataLoader implements CommandLineRunner {

    private final InvoiceRepository invoiceRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public TestDataLoader(InvoiceRepository invoiceRepository,
                          RoleRepository roleRepository,
                          UserRepository userRepository,
                          PasswordEncoder passwordEncoder) {
        this.invoiceRepository = invoiceRepository;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {

        String[] roles = {"ROLE_ADMIN", "ROLE_ACCOUNTANT", "ROLE_USER"};

        for (Role_enum roleEnum : Role_enum.values()) {
            String username = roleEnum.name().toLowerCase();
            if (!userRepository.existsByUsername(username)) {
                User user = new User();
                user.setUsername(username);
                user.setName(username);
                user.setPassword(passwordEncoder.encode("test123"));
                user.setRoles(Set.of(roleRepository.findByName(roleEnum)));
                userRepository.save(user);
            }
        }

        if (invoiceRepository.count() == 0) {
            IntStream.rangeClosed(1, 30).forEach(i -> {
                Invoice invoice = new Invoice();
                invoice.setCustomerName("Teszt Ügyfél " + i);
                invoice.setIssueDate(LocalDate.now().minusDays(i));
                invoice.setDueDate(LocalDate.now().plusDays(30 - i));
                invoice.setItemName("Termék " + i);
                invoice.setComment("Megjegyzés a(z) " + i + ". számlához");
                invoice.setPrice(1000.0 + i * 100);

                invoiceRepository.save(invoice);
            });
        }
    }
}


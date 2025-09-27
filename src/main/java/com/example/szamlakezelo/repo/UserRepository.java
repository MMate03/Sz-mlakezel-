package com.example.szamlakezelo.repo;

import com.example.szamlakezelo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}

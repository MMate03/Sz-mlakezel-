package com.example.szamlakezelo.repo;

import com.example.szamlakezelo.model.Role;
import com.example.szamlakezelo.model.Role_enum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface RoleRepository extends JpaRepository<Role, Long>
{
    Role findByName(Role_enum roleUser);

    Set<Role> findByNameIn(List<String> roles);
}

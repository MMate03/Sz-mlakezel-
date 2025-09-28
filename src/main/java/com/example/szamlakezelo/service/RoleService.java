package com.example.szamlakezelo.service;

import com.example.szamlakezelo.model.Role;
import com.example.szamlakezelo.model.Role_enum;
import com.example.szamlakezelo.repo.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class RoleService {

    private RoleRepository roleRepository;


    @Autowired
    public void  setRoleRepository(RoleRepository roleRepository) {this.roleRepository = roleRepository;}

    public List<Role> getRolesNoAdmin() {
        return roleRepository.findAll()
                .stream()
                .filter(role -> role.getName() != Role_enum.ROLE_ADMIN)
                .toList();
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
}

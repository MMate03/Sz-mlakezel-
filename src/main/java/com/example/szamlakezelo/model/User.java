package com.example.szamlakezelo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.Data;


import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Entity
@Data
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    private LocalDate loginDate;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )

    private Set<Role> role = new HashSet<>();

    public Set<Role> getRoles() {
        return role;
    }

    public void setRoles(Set<Role> newRoles) {
        this.role = newRoles;

    }


}

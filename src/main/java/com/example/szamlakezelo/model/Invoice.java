package com.example.szamlakezelo.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "invoice")
public class Invoice {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String customerName;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDate issueDate;

    @Column(nullable = false)
    private LocalDate dueDate;

    @Column(nullable = false)
    private String itemName;

    @Column(nullable = false)
    private String comment;

    @Column(nullable = false)
    private Double price;
}

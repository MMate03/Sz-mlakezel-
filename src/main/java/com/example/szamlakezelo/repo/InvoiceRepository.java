package com.example.szamlakezelo.repo;

import com.example.szamlakezelo.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
}

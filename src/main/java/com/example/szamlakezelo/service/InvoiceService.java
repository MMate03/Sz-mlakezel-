package com.example.szamlakezelo.service;

import com.example.szamlakezelo.model.Invoice;
import com.example.szamlakezelo.repo.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoiceService {
    private InvoiceRepository invoiceRepository;

    @Autowired
    public InvoiceService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    public List<Invoice> findAll() {
        return invoiceRepository.findAll();
    }

    public void save(Invoice invoice) {
        invoiceRepository.save(invoice);
    }
}

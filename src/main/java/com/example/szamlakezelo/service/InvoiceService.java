package com.example.szamlakezelo.service;

import com.example.szamlakezelo.model.Invoice;
import com.example.szamlakezelo.repo.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

        if (invoice.getCustomerName() == null || invoice.getCustomerName().trim().length() < 3) {
            throw new IllegalArgumentException("Az ügyfél neve legalább 3 karakter hosszú kell legyen!");
        }

        if (invoice.getIssueDate() == null) {
            throw new IllegalArgumentException("A kibocsátás dátuma kötelező!");
        }
        if (invoice.getIssueDate().isAfter(java.time.LocalDate.now())) {
            throw new IllegalArgumentException("A kibocsátás dátuma nem lehet a jövőben!");
        }

        if (invoice.getDueDate() == null) {
            throw new IllegalArgumentException("A fizetési határidő kötelező!");
        }
        if (invoice.getDueDate().isBefore(invoice.getIssueDate())) {
            throw new IllegalArgumentException("A fizetési határidő nem lehet a kibocsátás dátuma előtt!");
        }

        if (invoice.getComment() != null && invoice.getComment().length() > 255) {
            throw new IllegalArgumentException("A megjegyzés legfeljebb 255 karakter lehet!");
        }

        invoiceRepository.save(invoice);
    }

    public Optional<Invoice> getInvoiceById(Long id) {
        return invoiceRepository.findById(id);
    }
}

package com.example.szamlakezelo.config;

import com.example.szamlakezelo.model.Invoice;
import com.example.szamlakezelo.repo.InvoiceRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.stream.IntStream;

@Component
public class TestDataLoader implements CommandLineRunner {

    private final InvoiceRepository invoiceRepository;

    public TestDataLoader(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    @Override
    public void run(String... args) {
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


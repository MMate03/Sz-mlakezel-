package com.example.szamlakezelo.controller;

import com.example.szamlakezelo.model.Invoice;
import com.example.szamlakezelo.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;

    @Autowired
    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping
    public String getInvoices(Authentication authentication, Model model) {
        List<Invoice> invoices = invoiceService.findAll();
        Set<String> roles = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        model.addAttribute("roles", roles);
        model.addAttribute("invoices", invoices);
        return "invoices";
    }

    @GetMapping("/add")
    public String addInvoice(Model model) {
        model.addAttribute("today", LocalDate.now());
        return "create_invoice";
    }

    @PostMapping("/add")
    public String addInvoice(Invoice invoice) {
        invoiceService.save(invoice);
        return "redirect:/invoices";
    }

    @GetMapping("/{id}")
    public String viewInvoice(@PathVariable Long id, Model model) {
        Invoice invoice = invoiceService.getInvoiceById(id)
                .orElseThrow(() -> new IllegalArgumentException("Nem található ilyen számla"));;
        model.addAttribute("invoice", invoice);
        return "invoice";
    }
}

package com.billing.controller;

import com.billing.entity.Billing;
import com.billing.repository.BillingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/billing")
public class BillingController {

    @Autowired
    private BillingRepository billingRepository;

    @GetMapping("/invoices")
    public ResponseEntity<List<Billing>> getAllInvoices() {
        List<Billing> invoices = billingRepository.findAll();
        return ResponseEntity.ok(invoices);
    }

    @GetMapping("/invoices/{id}")
    public ResponseEntity<Billing> getInvoiceById(@PathVariable Long id) {
        return billingRepository.findById(id)
                .map(invoice -> ResponseEntity.ok(invoice))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/invoices")
    public ResponseEntity<Billing> createInvoice(@RequestBody Billing billing) {
        Billing savedInvoice = billingRepository.save(billing);
        return ResponseEntity.ok(savedInvoice);
    }

    @PutMapping("/invoices/{id}")
    public ResponseEntity<Billing> updateInvoice(@PathVariable Long id, @RequestBody Billing updatedBilling) {
        if (!billingRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        updatedBilling.setId(id);
        Billing savedInvoice = billingRepository.save(updatedBilling);
        return ResponseEntity.ok(savedInvoice);
    }

    @DeleteMapping("/invoices/{id}")
    public ResponseEntity<Void> deleteInvoice(@PathVariable Long id) {
        if (!billingRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        billingRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}




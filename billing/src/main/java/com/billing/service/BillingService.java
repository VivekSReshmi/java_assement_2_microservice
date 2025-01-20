package com.billing.service;



import com.billing.entity.Billing;
import com.billing.repository.BillingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BillingService {

    @Autowired
    private BillingRepository billingRepository;

    public List<Billing> getAllInvoices() {
        return billingRepository.findAll();
    }

    public Optional<Billing> getInvoiceById(Long id) {
        return billingRepository.findById(id);
    }

    public Billing createInvoice(Billing billing) {
        // Add any business logic or validation here
        return billingRepository.save(billing);
    }

    public Billing updateInvoice(Long id, Billing updatedBilling) {
        if (!billingRepository.existsById(id)) {
            throw new IllegalArgumentException("Invoice not found");
        }
        updatedBilling.setId(id);
        return billingRepository.save(updatedBilling);
    }

    public void deleteInvoice(Long id) {
        if (!billingRepository.existsById(id)) {
            throw new IllegalArgumentException("Invoice not found");
        }
        billingRepository.deleteById(id);
    }
}


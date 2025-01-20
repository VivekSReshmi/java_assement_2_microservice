package com.billing.entity;


import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "billing")
public class Billing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "invoice_number", nullable = false, unique = true)
    private String invoiceNumber;

    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "billing_date", nullable = true)
    private LocalDate billingDate;

    @Column(name = "due_date", nullable = true)
    private LocalDate dueDate;

    @Column(name = "status", nullable = false)
    private String status;

    // Constructors, getters, and setters

    public Billing() {
    }

    public Billing(String invoiceNumber, Long customerId, BigDecimal amount, LocalDate billingDate, LocalDate dueDate, String status) {
        this.invoiceNumber = invoiceNumber;
        this.customerId = customerId;
        this.amount = amount;
        this.billingDate = billingDate;
        this.dueDate = dueDate;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getBillingDate() {
        return billingDate;
    }

    public void setBillingDate(LocalDate billingDate) {
        this.billingDate = billingDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Billing{" +
                "id=" + id +
                ", invoiceNumber='" + invoiceNumber + '\'' +
                ", customerId=" + customerId +
                ", amount=" + amount +
                ", billingDate=" + billingDate +
                ", dueDate=" + dueDate +
                ", status='" + status + '\'' +
                '}';
    }
}

package com.diksha.bankingSystem_springBoot.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;
@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;
    private double amount;
    @Column(nullable = false)
    private LocalDateTime timestamp;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name="account_id", nullable =false)
    private Account account;
    @PrePersist
    public void setTimestamp() {
        this.timestamp =LocalDateTime.now();
    }

    public Transaction(){}
    public Transaction(String type,double amount,Account account){
        this.type=type;
        this.amount=amount;
        this.account=account;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
    @Transient  // DB mein save nahi hoga, sirf request ke liye
    private Long toAccountId;

    public Long getToAccountId() { return toAccountId; }
    public void setToAccountId(Long toAccountId) { this.toAccountId = toAccountId; }
}

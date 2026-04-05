package com.diksha.bankingSystem_springBoot.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message="Name cannot be empty")
    private String name;
    @NotBlank(message="PIN cannot be empty")
    @Size(min=4 , max=4,message="PIN must be 4 digits")
    private String pin;
    private double balance;
    public Account(){

    }
    public Account(String name,String pin){
        this.name=name;
        this.pin=pin;
        this.balance=0.0;
    }
    @JsonManagedReference
    @OneToMany(mappedBy = "account",cascade=CascadeType.ALL)

    private List<Transaction> transactions = new ArrayList<>();
    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    public String getPin() {
        return pin;
    }

    public double getBalance() {
        return balance;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}

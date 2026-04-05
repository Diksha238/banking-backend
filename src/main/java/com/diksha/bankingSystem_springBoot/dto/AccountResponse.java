package com.diksha.bankingSystem_springBoot.dto;

public class AccountResponse {
    private Long id;
    private String name;
    private double balance;
    public AccountResponse(Long id,String name,double balance){
        this.id=id;
        this.name=name;
        this.balance=balance;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }
}

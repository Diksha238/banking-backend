package com.diksha.bankingSystem_springBoot.dto;

import jakarta.validation.constraints.Positive;

public class AmountRequest {
    @Positive(message="Amount must be greater than 0")
    private double amount;
    public double getAmount(){
        return amount;
    }
    public void setAmount(double amount){
        this.amount=amount;
    }
}

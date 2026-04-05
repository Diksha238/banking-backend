package com.diksha.bankingSystem_springBoot.dto;

import jakarta.validation.constraints.NotBlank;

public class LoginRequest {
    private Long id;

    @NotBlank(message = "PIN cannot be empty")
    private String pin;

    public Long getId() {
        return id;
    }

    public String getPin() {
        return pin;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
}

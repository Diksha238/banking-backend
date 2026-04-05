package com.diksha.bankingSystem_springBoot.controller;

import com.diksha.bankingSystem_springBoot.Entity.Account;
import com.diksha.bankingSystem_springBoot.Entity.Transaction;
import com.diksha.bankingSystem_springBoot.dto.AccountResponse;
import com.diksha.bankingSystem_springBoot.dto.AmountRequest;
import com.diksha.bankingSystem_springBoot.dto.LoginRequest;
import com.diksha.bankingSystem_springBoot.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/accounts")
public class AccountController {
    private final AccountService accountService;
    public AccountController(AccountService accountService){
        this.accountService=accountService;
    }
    @PostMapping
    public AccountResponse createAccount(@Valid @RequestBody Account account){
        Account saved= accountService.createAccount(account.getName(), account.getPin());
        return new AccountResponse(saved.getId(),saved.getName(),saved.getBalance());
    }
    @GetMapping("/{id}")
    public Account getAccountById(@PathVariable Long id){
        return accountService.getAccountById(id);
    }
    @PostMapping("/{id}/deposit")
    public AccountResponse deposit(@PathVariable Long id , @Valid @RequestBody AmountRequest request) {
        Account updated=accountService.deposit(id,request.getAmount());
        return new AccountResponse(
                updated.getId(),
                updated.getName(),
                updated.getBalance()
        );
    }
    @PostMapping("/{id}/withdraw")
    public AccountResponse withdraw(@PathVariable Long id,@Valid @RequestBody AmountRequest request){
        Account updated=accountService.withdraw(id,request.getAmount());
        return new AccountResponse(
                updated.getId(),
                updated.getName(),
                updated.getBalance()
        );
    }
    @GetMapping("/{id}/transactions")
    public List<Transaction> getTransactions(@PathVariable Long id) {
        Account account = accountService.getAccountById(id);
        return account.getTransactions();
    }
    @PostMapping("/login")
    public AccountResponse login(@Valid @RequestBody LoginRequest request) {

        Account account = accountService.login(request.getId(), request.getPin());

        return new AccountResponse(
                account.getId(),
                account.getName(),
                account.getBalance()
        );
    }
}

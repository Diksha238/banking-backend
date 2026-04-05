package com.diksha.bankingSystem_springBoot.service;

import com.diksha.bankingSystem_springBoot.Entity.Account;
import com.diksha.bankingSystem_springBoot.Entity.Transaction;
import com.diksha.bankingSystem_springBoot.Repository.AccountRepository;
import com.diksha.bankingSystem_springBoot.Repository.TransactionRepository;
import com.diksha.bankingSystem_springBoot.exception.InsufficientBalanceException;
import com.diksha.bankingSystem_springBoot.exception.InvalidPinException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final TransactionRepository trannsactionRepository;
    public  AccountService(AccountRepository accountRepository, TransactionRepository trannsactionRepository){
        this.accountRepository = accountRepository;
        this.trannsactionRepository=trannsactionRepository;
    }
    public Account createAccount(String name, String pin){
        return accountRepository.save(new Account(name,pin));
    }
    public Account getAccountById(Long id){
        return accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Account Not FOUND"));
    }
    public Account deposit(Long id,double amount){
        Account account = accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Account Not Found"));
        account.setBalance(account.getBalance() + amount);
        Transaction transaction=new Transaction("Deposit",amount,account);
        Transaction t = new Transaction();
        t.setType("DEPOSIT");
        t.setAmount(amount);
        t.setAccount(account);


        trannsactionRepository.save(t);
        return accountRepository.save(account);

    }
    public Account withdraw(Long id,double amount){
        Account account =accountRepository.findById(id).orElseThrow(()-> new RuntimeException("Account NOT FOUND"));
        if(amount>account.getBalance()){
            throw new InsufficientBalanceException("Insufficient balance");
        }
        account.setBalance(account.getBalance()-amount);
        Transaction transaction=new Transaction("Withdraw",amount,account);
        Transaction t = new Transaction();
        t.setType("WITHDRAW");
        t.setAmount(amount);
        t.setAccount(account);

        trannsactionRepository.save(t);
        return accountRepository.save(account);
    }
    public Account login(Long id, String pin) {

        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if (!account.getPin().equals(pin)) {
            throw new InvalidPinException("Invalid PIN");
        }

        return account;
    }
}

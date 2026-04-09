package com.diksha.bankingSystem_springBoot.controller;
import com.diksha.bankingSystem_springBoot.Entity.Transaction;
import com.diksha.bankingSystem_springBoot.Repository.TransactionRepository;
import com.diksha.bankingSystem_springBoot.service.AccountService;
import com.diksha.bankingSystem_springBoot.service.FraudDetectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/transactions")
public class TransactionController {
    @Autowired
    private FraudDetectionService fraudService;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private AccountService accountService;
    @PostMapping("/transfer")
    public String transferMoney(@RequestBody Transaction transaction){

        double hour = LocalDateTime.now().getHour();
        double amount = transaction.getAmount();
        double isNight = (hour >= 23 || hour <= 5) ? 1.0 : 0.0;
        double isHighAmount = amount > 10000 ? 1.0 : 0.0;
        double isWeekend = LocalDateTime.now().getDayOfWeek().getValue() >= 6 ? 1.0 : 0.0;
        List<Double> features = Arrays.asList(
                hour, amount, isNight, isHighAmount, isWeekend,
                0.0, 0.0, 0.0, 0.0, 0.0,  // V5-V9
                0.0, 0.0, 0.0, 0.0, 0.0,  // V10-V14
                0.0, 0.0, 0.0, 0.0, 0.0,  // V15-V19
                0.0, 0.0, 0.0, 0.0, 0.0,  // V20-V24
                0.0, 0.0, 0.0, 0.0, amount // V25-V28, Amount
        );

        Map<String, Object> result = fraudService.checkFraud(features);
        Double probability = ((Number) result.get("probability")).doubleValue();
        System.out.println("FRAUD PROBABILITY: " + probability);

        if (probability > 0.001) {
            return "Transaction Blocked - Fraud Detected";
        } else if (probability > 0.0005) {
            return "Transaction Flagged - Manual Review Required";
        }

        // Actual balance transfer
        accountService.transfer(
                transaction.getAccount().getId(),
                transaction.getToAccountId(),
                transaction.getAmount()
        );

        return "Transaction Successful";
    }

}

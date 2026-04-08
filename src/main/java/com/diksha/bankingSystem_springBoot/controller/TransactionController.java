package com.diksha.bankingSystem_springBoot.controller;
import com.diksha.bankingSystem_springBoot.Entity.Transaction;
import com.diksha.bankingSystem_springBoot.Repository.TransactionRepository;
import com.diksha.bankingSystem_springBoot.service.FraudDetectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    @Autowired
    private FraudDetectionService fraudService;
    @Autowired
    private TransactionRepository transactionRepository;
    @PostMapping("/transfer")
    public String transferMoney(@RequestBody Transaction transaction){
        transactionRepository.save(transaction);

        double hour = transaction.getTimestamp().getHour();
        double amount = transaction.getAmount();
        double isNight = (hour >= 23 || hour <= 5) ? 1.0 : 0.0;
        double isHighAmount = amount > 10000 ? 1.0 : 0.0;
        double isWeekend = transaction.getTimestamp().getDayOfWeek().getValue() >= 6 ? 1.0 : 0.0;

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
        System.out.println("FRAUD PROBABILITY: " + probability); // add karo

        if (probability > 0.001) {
            return "Transaction Blocked - Fraud Detected";
        } else if (probability > 0.0005) {
            return "Transaction Flagged - Manual Review Required";
        } else {
            return "Transaction Successful";
        }
    }
}

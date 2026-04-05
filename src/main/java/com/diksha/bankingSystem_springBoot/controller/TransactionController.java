package com.diksha.bankingSystem_springBoot.controller;

import com.diksha.bankingSystem_springBoot.Entity.Transaction;
import com.diksha.bankingSystem_springBoot.service.FraudDetectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    @Autowired
    private FraudDetectionService fraudService;
    @PostMapping("/transfer")
    public String transferMoney(@RequestBody Transaction transaction){
        List<Double> features = Arrays.asList(
                0.0d,
                (double) transaction.getAmount(),
                0.1d,0.2d,0.3d,0.4d,0.5d,0.6d,
                0.7d,0.8d,0.9d,1.0d,1.1d,1.2d,
                1.3d,1.4d,1.5d,1.6d,1.7d,1.8d,
                1.9d,2.0d,2.1d,2.2d,2.3d,2.4d,
                2.5d,2.6d,2.7d,2.8d
        );
        Map<String,Object> result=fraudService.checkFraud(features);
        Integer fraud=(Integer) result.get("fraud");
        Double probability = (Double) result.get("probability");
        if(probability>0.8){
            return "Transaction Blocked";

        }else if(probability >0.5){
            return "Transaction Flagged";
        }else{
            return "Transaction Successful";
        }
    }
}

package com.diksha.bankingSystem_springBoot.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class FraudDetectionService {
    private final RestTemplate restTemplate= new RestTemplate();
    public Map<String,Object> checkFraud(List<Double> features){
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(5000); // 5 sec
        factory.setReadTimeout(5000);    // 5 sec

        RestTemplate restTemplate = new RestTemplate(factory);

        String url = "https://your-ml-api.onrender.com/predict";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

// Body create
        Map<String, Object> body = new HashMap<>();
        body.put("data", features);

        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonBody = mapper.writeValueAsString(body);

            HttpEntity<String> request =
                    new HttpEntity<>(jsonBody, headers);  // ✅ yahi important hai

            ResponseEntity<String> response =
                    restTemplate.postForEntity(url, request, String.class);

            Map<String, Object> result =
                    mapper.readValue(response.getBody(), Map.class);

            return result;

        } catch (Exception e) {
            System.out.println("ML API timeout or error");

            Map<String, Object> fallback = new HashMap<>();
            fallback.put("fraud", 0);
            fallback.put("probability", 0.0);

            return fallback;
        }
    }
}

package com.jpmc.midascore.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api") // Base path for all endpoints
public class BalanceController {

    private final Map<String, Double> userBalances = new HashMap<>();

    // Constructor: Add sample data for Wilbur
    public BalanceController() {
        userBalances.put("wilbur", 1000.0);
    }

    // GET /api/balance?userId=wilbur
    @GetMapping("/balance")
    public ResponseEntity<Double> getBalance(@RequestParam String userId) {
        Double balance = userBalances.getOrDefault(userId, 0.0);
        return ResponseEntity.ok(balance);
    }
}

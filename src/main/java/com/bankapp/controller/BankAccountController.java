package com.bankapp.controller;

import com.bankapp.model.BankAccount;
import com.bankapp.service.BankAccountService;
import com.bankapp.dto.TransferRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class BankAccountController {
    private final BankAccountService bankAccountService;

    public BankAccountController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }
    
    @GetMapping("/balance")
    public ResponseEntity<?> getBalance(@AuthenticationPrincipal String username) {
        return bankAccountService.getBalance(username);
    }
    
    @PostMapping("/transfer")
    public ResponseEntity<?> transfer(
        @AuthenticationPrincipal String username,
        @RequestBody TransferRequest request
    ) {
        return bankAccountService.transfer(username, request);
    }
    
    @PostMapping("/deposit")
    public ResponseEntity<?> deposit(
        @AuthenticationPrincipal String username,
        @RequestBody Map<String, Double> request
    ) {
        return bankAccountService.deposit(username, request.get("amount"));
    }

    @PostMapping("/withdraw")
    public ResponseEntity<?> withdraw(
        @AuthenticationPrincipal String username,
        @RequestBody Map<String, Double> request
    ) {
        return bankAccountService.withdraw(username, request.get("amount"));
    }

    @PostMapping("/account")
    public ResponseEntity<?> createAccount(
        @AuthenticationPrincipal String username,
        @RequestBody Map<String, Double> request
    ) {
        return bankAccountService.createAccount(username, request.get("balance"));
    }
    
    @GetMapping("/admin/accounts")
    public ResponseEntity<?> getAllAccounts() {
        return bankAccountService.getAllAccounts();
    }
    
    @GetMapping("/admin/transactions")
    public ResponseEntity<?> getAllTransactions() {
        return bankAccountService.getAllTransactions();
    }
}

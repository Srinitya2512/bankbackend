package com.bankapp.controller;

import com.bankapp.model.BankAccount;
import com.bankapp.repository.BankAccountRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class BankAccountController {
    private final BankAccountRepository repo;
    //private static Long nextId = 1L;
    public BankAccountController(BankAccountRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<BankAccount> getAllAccounts() {
        return repo.findAll();
    }

    @PostMapping
    public ResponseEntity<?> createAccount(@RequestBody CreateAccountRequest request) {
        try {
            if (request.getOwner() == null || request.getOwner().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Owner name is required");
            }
            if (request.getBalance() < 0) {
                return ResponseEntity.badRequest().body("Balance cannot be negative");
            }
            
            BankAccount account = new BankAccount();
          //  account.setId(nextId++);
            account.setOwner(request.getOwner());
            account.setBalance(request.getBalance());
            BankAccount savedAccount = repo.save(account);
            
            return ResponseEntity.ok(savedAccount);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                               .body("Error creating account: " + e.getMessage());
        }
    }

    @PutMapping("/{id}/deposit")
    public BankAccount deposit(@PathVariable Long id, @RequestBody double amount) {
        BankAccount acc = repo.findById(id).orElseThrow();
        acc.setBalance(acc.getBalance() + amount);
        return repo.save(acc);
    }

    @PutMapping("/{id}/withdraw")
    public BankAccount withdraw(@PathVariable Long id, @RequestBody double amount) {
        BankAccount acc = repo.findById(id).orElseThrow();
        if (acc.getBalance() >= amount) {
            acc.setBalance(acc.getBalance() - amount);
            return repo.save(acc);
        }
        throw new IllegalArgumentException("Insufficient funds");
    }
}

class CreateAccountRequest {
    private String owner;
    private double balance;

    // Getters and setters
    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }
    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }
}

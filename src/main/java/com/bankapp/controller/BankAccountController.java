package com.bankapp.controller;

import com.bankapp.model.BankAccount;
import com.bankapp.repository.BankAccountRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class BankAccountController {
    private final BankAccountRepository repo;

    public BankAccountController(BankAccountRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<BankAccount> getAllAccounts() {
        return repo.findAll();
    }

    @PostMapping
    public BankAccount createAccount(@RequestBody BankAccount account) {
        return repo.save(account);
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

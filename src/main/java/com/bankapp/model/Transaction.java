package com.bankapp.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String type; // DEPOSIT, WITHDRAW, TRANSFER
    private double amount;
    private LocalDateTime timestamp;
    
    @ManyToOne
    private BankAccount account;
    
    @ManyToOne
    private BankAccount targetAccount; // For transfers
    
    // Getters and setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
    public BankAccount getAccount() {
        return account;
    }
    public void setAccount(BankAccount account) {
        this.account = account;
    }
    public BankAccount getTargetAccount() {
        return targetAccount;
    }
    public void setTargetAccount(BankAccount targetAccount) {
        this.targetAccount = targetAccount;
    }
    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", amount=" + amount +
                ", timestamp=" + timestamp +
                ", account=" + account +
                ", targetAccount=" + targetAccount +
                '}';
    }
    
}
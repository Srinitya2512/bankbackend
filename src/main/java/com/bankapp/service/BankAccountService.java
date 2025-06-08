package com.bankapp.service;

import com.bankapp.model.BankAccount;
import com.bankapp.model.Transaction;
import com.bankapp.repository.BankAccountRepository;
import com.bankapp.repository.TransactionRepository;
import com.bankapp.dto.TransferRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BankAccountService {
    private final BankAccountRepository accountRepo;
    private final TransactionRepository transactionRepo;

    public ResponseEntity<?> getBalance(String username) {
        BankAccount account = accountRepo.findByOwner(username);
        if (account == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found");
        }
        return ResponseEntity.ok(account.getBalance());
    }

    public ResponseEntity<?> getAllAccounts() {
        List<BankAccount> accounts = accountRepo.findAll();
        return ResponseEntity.ok(accounts);
    }

    public ResponseEntity<?> getAllTransactions() {
        List<Transaction> transactions = transactionRepo.findAll();
        return ResponseEntity.ok(transactions);
    }

    @Transactional
    public ResponseEntity<?> transfer(String username, TransferRequest request) {
        BankAccount senderAccount = accountRepo.findByOwner(username);
        if (senderAccount == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sender account not found");
        }
        BankAccount recipientAccount = accountRepo.findByOwner(request.getRecipient());
        if (recipientAccount == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Recipient account not found");
        }
        if (senderAccount.getBalance() < request.getAmount()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Insufficient funds");
        }
        senderAccount.setBalance(senderAccount.getBalance() - request.getAmount());
        recipientAccount.setBalance(recipientAccount.getBalance() + request.getAmount());
        Transaction transaction = new Transaction();
        transaction.setType("TRANSFER");
        transaction.setAmount(request.getAmount());
        transaction.setTimestamp(LocalDateTime.now());
        transaction.setAccount(senderAccount);
        transaction.setTargetAccount(recipientAccount);
        transactionRepo.save(transaction);
        accountRepo.save(senderAccount);
        accountRepo.save(recipientAccount);
        return ResponseEntity.ok("Transfer successful");
    }

    @Transactional
    public ResponseEntity<?> deposit(String username, double amount) {
        BankAccount account = accountRepo.findByOwner(username);
        if (account == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found");
        }
        if (amount <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Deposit amount must be positive");
        }
        account.setBalance(account.getBalance() + amount);
        Transaction transaction = new Transaction();
        transaction.setType("DEPOSIT");
        transaction.setAmount(amount);
        transaction.setTimestamp(LocalDateTime.now());
        transaction.setAccount(account);
        transactionRepo.save(transaction);
        accountRepo.save(account);
        return ResponseEntity.ok("Deposit successful");
    }

    @Transactional
    public ResponseEntity<?> withdraw(String username, double amount) {
        BankAccount account = accountRepo.findByOwner(username);
        if (account == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found");
        }
        if (amount <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Withdrawal amount must be positive");
        }
        if (account.getBalance() < amount) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Insufficient funds");
        }
        account.setBalance(account.getBalance() - amount);
        Transaction transaction = new Transaction();
        transaction.setType("WITHDRAW");
        transaction.setAmount(amount);
        transaction.setTimestamp(LocalDateTime.now());
        transaction.setAccount(account);
        transactionRepo.save(transaction);
        accountRepo.save(account);
        return ResponseEntity.ok("Withdrawal successful");
    }

    @Transactional
    public ResponseEntity<?> createAccount(String username, double initialBalance) {
        if (accountRepo.findByOwner(username) != null) {
            return ResponseEntity.badRequest().body("User already has an account");
        }

        BankAccount account = new BankAccount();
        account.setOwner(username);
        account.setBalance(initialBalance);
        accountRepo.save(account);

        return ResponseEntity.ok("Account created successfully");
    }
}
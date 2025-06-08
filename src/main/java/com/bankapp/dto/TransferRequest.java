package com.bankapp.dto;

import lombok.Data;

@Data
public class TransferRequest {
    private String recipient;
    private double amount;
}
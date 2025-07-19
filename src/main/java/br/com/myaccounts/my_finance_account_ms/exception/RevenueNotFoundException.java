package br.com.myaccounts.my_finance_account_ms.exception;

import java.util.UUID;

public class RevenueNotFoundException extends RuntimeException {

    public RevenueNotFoundException(String message) {
        super(message);
    }

    public RevenueNotFoundException(UUID revenueId) {
        super("Revenue not found with ID: " + revenueId);
    }
}

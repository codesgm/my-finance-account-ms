package br.com.myaccounts.my_finance_account_ms.exception;

import java.util.UUID;

public class ExpenseNotFoundException extends RuntimeException {

    public ExpenseNotFoundException(String message) {
        super(message);
    }

    public ExpenseNotFoundException(UUID expenseId) {
        super("Expense not found with ID: " + expenseId);
    }
}

package br.com.myaccounts.my_finance_account_ms.exception;

import java.util.UUID;

public class CategoryNotFoundException extends RuntimeException {

    public CategoryNotFoundException(String message) {
        super(message);
    }

    public CategoryNotFoundException(UUID categoryId) {
        super("Category not found with ID: " + categoryId);
    }
}

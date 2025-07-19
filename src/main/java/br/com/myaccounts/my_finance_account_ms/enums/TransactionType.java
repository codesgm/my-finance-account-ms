package br.com.myaccounts.my_finance_account_ms.enums;

import lombok.Getter;

@Getter
public enum TransactionType {
    REVENUE("REVENUE"),
    EXPENSE("EXPENSE");

    private final String value;

    TransactionType(String value) {
        this.value = value;
    }

    public static TransactionType fromValue(String value) {
        for (TransactionType type : TransactionType.values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid TransactionType value: " + value);
    }
}

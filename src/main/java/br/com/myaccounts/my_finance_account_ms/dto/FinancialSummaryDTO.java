package br.com.myaccounts.my_finance_account_ms.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FinancialSummaryDTO {

    private BigDecimal totalRevenues;
    private BigDecimal totalExpenses;
    private BigDecimal balance;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long totalRevenueTransactions;
    private Long totalExpenseTransactions;

    public FinancialSummaryDTO(BigDecimal totalRevenues, BigDecimal totalExpenses, LocalDate startDate, LocalDate endDate) {
        this.totalRevenues = totalRevenues != null ? totalRevenues : BigDecimal.ZERO;
        this.totalExpenses = totalExpenses != null ? totalExpenses : BigDecimal.ZERO;
        this.startDate = startDate;
        this.endDate = endDate;
        calculateBalance();
    }

    public void calculateBalance() {
        BigDecimal revenues = totalRevenues != null ? totalRevenues : BigDecimal.ZERO;
        BigDecimal expenses = totalExpenses != null ? totalExpenses : BigDecimal.ZERO;
        this.balance = revenues.subtract(expenses);
    }

    public boolean hasPositiveBalance() {
        return balance != null && balance.compareTo(BigDecimal.ZERO) > 0;
    }

    public boolean hasNegativeBalance() {
        return balance != null && balance.compareTo(BigDecimal.ZERO) < 0;
    }
}

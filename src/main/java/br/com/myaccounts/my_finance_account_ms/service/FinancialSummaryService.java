package br.com.myaccounts.my_finance_account_ms.service;

import br.com.myaccounts.my_finance_account_ms.dto.FinancialSummaryDTO;
import br.com.myaccounts.my_finance_account_ms.exception.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class FinancialSummaryService {

    private final RevenueService revenueService;
    private final ExpenseService expenseService;

    public BigDecimal calculateBalance(UUID userId, LocalDate startDate, LocalDate endDate) {
        log.debug("Calculating balance for user ID: {} between {} and {}", userId, startDate, endDate);
        
        if (startDate.isAfter(endDate)) {
            throw new ValidationException("Start date cannot be after end date");
        }
        
        BigDecimal totalRevenues = revenueService.calculateTotalByPeriod(userId, startDate, endDate);
        BigDecimal totalExpenses = expenseService.calculateTotalByPeriod(userId, startDate, endDate);
        
        return totalRevenues.subtract(totalExpenses);
    }

    public FinancialSummaryDTO getFinancialSummary(UUID userId, LocalDate startDate, LocalDate endDate) {
        log.debug("Getting financial summary for user ID: {} between {} and {}", userId, startDate, endDate);
        
        if (startDate.isAfter(endDate)) {
            throw new ValidationException("Start date cannot be after end date");
        }
        
        BigDecimal totalRevenues = revenueService.calculateTotalByPeriod(userId, startDate, endDate);
        BigDecimal totalExpenses = expenseService.calculateTotalByPeriod(userId, startDate, endDate);
        
        FinancialSummaryDTO summary = new FinancialSummaryDTO(totalRevenues, totalExpenses, startDate, endDate);
        
        // Get transaction counts
        Long revenueCount = getRevenueCountByPeriod(userId, startDate, endDate);
        Long expenseCount = getExpenseCountByPeriod(userId, startDate, endDate);
        
        summary.setTotalRevenueTransactions(revenueCount);
        summary.setTotalExpenseTransactions(expenseCount);
        
        return summary;
    }

    public FinancialSummaryDTO getCurrentMonthSummary(UUID userId) {
        log.debug("Getting current month summary for user ID: {}", userId);
        
        LocalDate now = LocalDate.now();
        LocalDate startOfMonth = now.withDayOfMonth(1);
        LocalDate endOfMonth = now.withDayOfMonth(now.lengthOfMonth());
        
        return getFinancialSummary(userId, startOfMonth, endOfMonth);
    }

    public FinancialSummaryDTO getYearSummary(UUID userId, int year) {
        log.debug("Getting year summary for user ID: {} and year: {}", userId, year);
        
        LocalDate startOfYear = LocalDate.of(year, 1, 1);
        LocalDate endOfYear = LocalDate.of(year, 12, 31);
        
        return getFinancialSummary(userId, startOfYear, endOfYear);
    }

    public List<FinancialSummaryDTO> getLastMonthsSummary(UUID userId, int months) {
        log.debug("Getting last {} months summary for user ID: {}", months, userId);
        
        if (months <= 0) {
            throw new ValidationException("Number of months must be positive");
        }
        
        List<FinancialSummaryDTO> summaries = new ArrayList<>();
        LocalDate currentDate = LocalDate.now();
        
        for (int i = 0; i < months; i++) {
            YearMonth yearMonth = YearMonth.from(currentDate.minusMonths(i));
            LocalDate startOfMonth = yearMonth.atDay(1);
            LocalDate endOfMonth = yearMonth.atEndOfMonth();
            
            FinancialSummaryDTO summary = getFinancialSummary(userId, startOfMonth, endOfMonth);
            summaries.add(summary);
        }
        
        return summaries;
    }

    private Long getRevenueCountByPeriod(UUID userId, LocalDate startDate, LocalDate endDate) {
        return (long) revenueService.findByUserAndPeriod(userId, startDate, endDate).size();
    }

    private Long getExpenseCountByPeriod(UUID userId, LocalDate startDate, LocalDate endDate) {
        return (long) expenseService.findByUserAndPeriod(userId, startDate, endDate).size();
    }
}

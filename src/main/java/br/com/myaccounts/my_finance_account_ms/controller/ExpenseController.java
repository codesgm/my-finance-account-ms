package br.com.myaccounts.my_finance_account_ms.controller;

import br.com.myaccounts.my_finance_account_ms.controller.doc.ExpenseControllerDoc;
import br.com.myaccounts.my_finance_account_ms.dto.CreateExpenseDTO;
import br.com.myaccounts.my_finance_account_ms.dto.ExpenseDTO;
import br.com.myaccounts.my_finance_account_ms.dto.UpdateExpenseDTO;
import br.com.myaccounts.my_finance_account_ms.service.ExpenseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users/{userId}/expenses")
@RequiredArgsConstructor
@Slf4j
public class ExpenseController implements ExpenseControllerDoc {

    private final ExpenseService expenseService;

    @Override
    @GetMapping
    public ResponseEntity<List<ExpenseDTO>> getExpenses(
            @PathVariable UUID userId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        log.debug("GET /api/users/{}/expenses?startDate={}&endDate={}", userId, startDate, endDate);
        
        List<ExpenseDTO> expenses;
        if (startDate != null && endDate != null) {
            expenses = expenseService.findByUserAndPeriod(userId, startDate, endDate);
        } else {
            expenses = expenseService.findByUser(userId);
        }
        
        return ResponseEntity.ok(expenses);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ExpenseDTO> getExpense(@PathVariable UUID id, @PathVariable UUID userId) {
        log.debug("GET /api/users/{}/expenses/{}", userId, id);
        ExpenseDTO expense = expenseService.findById(id, userId);
        return ResponseEntity.ok(expense);
    }

    @Override
    @PostMapping
    public ResponseEntity<ExpenseDTO> createExpense(
            @PathVariable UUID userId, 
            @Valid @RequestBody CreateExpenseDTO dto) {
        log.debug("POST /api/users/{}/expenses", userId);
        ExpenseDTO expense = expenseService.create(userId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(expense);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<ExpenseDTO> updateExpense(
            @PathVariable UUID id, 
            @PathVariable UUID userId, 
            @Valid @RequestBody UpdateExpenseDTO dto) {
        log.debug("PUT /api/users/{}/expenses/{}", userId, id);
        ExpenseDTO expense = expenseService.update(id, userId, dto);
        return ResponseEntity.ok(expense);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable UUID id, @PathVariable UUID userId) {
        log.debug("DELETE /api/users/{}/expenses/{}", userId, id);
        expenseService.delete(id, userId);
        return ResponseEntity.noContent().build();
    }

    @Override
    @GetMapping("/total")
    public ResponseEntity<BigDecimal> getTotalByPeriod(
            @PathVariable UUID userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        log.debug("GET /api/users/{}/expenses/total?startDate={}&endDate={}", userId, startDate, endDate);
        BigDecimal total = expenseService.calculateTotalByPeriod(userId, startDate, endDate);
        return ResponseEntity.ok(total);
    }

    @Override
    @GetMapping("/count")
    public ResponseEntity<Long> getExpenseCount(@PathVariable UUID userId) {
        log.debug("GET /api/users/{}/expenses/count", userId);
        Long count = expenseService.getExpenseCount(userId);
        return ResponseEntity.ok(count);
    }

    @Override
    @GetMapping("/recent")
    public ResponseEntity<List<ExpenseDTO>> getRecentExpenses(@PathVariable UUID userId) {
        log.debug("GET /api/users/{}/expenses/recent", userId);
        List<ExpenseDTO> expenses = expenseService.getRecentExpenses(userId);
        return ResponseEntity.ok(expenses);
    }
}

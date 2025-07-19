package br.com.myaccounts.my_finance_account_ms.controller;

import br.com.myaccounts.my_finance_account_ms.controller.doc.FinancialSummaryControllerDoc;
import br.com.myaccounts.my_finance_account_ms.dto.FinancialSummaryDTO;
import br.com.myaccounts.my_finance_account_ms.service.FinancialSummaryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users/{userId}/financial-summary")
@RequiredArgsConstructor
@Slf4j
public class FinancialSummaryController implements FinancialSummaryControllerDoc {

    private final FinancialSummaryService financialSummaryService;

    @Override
    @GetMapping
    public ResponseEntity<FinancialSummaryDTO> getFinancialSummary(
            @PathVariable UUID userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        log.debug("GET /api/users/{}/financial-summary?startDate={}&endDate={}", userId, startDate, endDate);
        FinancialSummaryDTO summary = financialSummaryService.getFinancialSummary(userId, startDate, endDate);
        return ResponseEntity.ok(summary);
    }

    @Override
    @GetMapping("/balance")
    public ResponseEntity<BigDecimal> getBalance(
            @PathVariable UUID userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        log.debug("GET /api/users/{}/financial-summary/balance?startDate={}&endDate={}", userId, startDate, endDate);
        BigDecimal balance = financialSummaryService.calculateBalance(userId, startDate, endDate);
        return ResponseEntity.ok(balance);
    }

    @Override
    @GetMapping("/current-month")
    public ResponseEntity<FinancialSummaryDTO> getCurrentMonthSummary(@PathVariable UUID userId) {
        log.debug("GET /api/users/{}/financial-summary/current-month", userId);
        FinancialSummaryDTO summary = financialSummaryService.getCurrentMonthSummary(userId);
        return ResponseEntity.ok(summary);
    }

    @Override
    @GetMapping("/year/{year}")
    public ResponseEntity<FinancialSummaryDTO> getYearSummary(
            @PathVariable UUID userId, 
            @PathVariable int year) {
        log.debug("GET /api/users/{}/financial-summary/year/{}", userId, year);
        FinancialSummaryDTO summary = financialSummaryService.getYearSummary(userId, year);
        return ResponseEntity.ok(summary);
    }

    @Override
    @GetMapping("/last-months/{months}")
    public ResponseEntity<List<FinancialSummaryDTO>> getLastMonthsSummary(
            @PathVariable UUID userId, 
            @PathVariable int months) {
        log.debug("GET /api/users/{}/financial-summary/last-months/{}", userId, months);
        List<FinancialSummaryDTO> summaries = financialSummaryService.getLastMonthsSummary(userId, months);
        return ResponseEntity.ok(summaries);
    }
}

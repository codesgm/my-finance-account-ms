package br.com.myaccounts.my_finance_account_ms.controller;

import br.com.myaccounts.my_finance_account_ms.controller.doc.RevenueControllerDoc;
import br.com.myaccounts.my_finance_account_ms.dto.CreateRevenueDTO;
import br.com.myaccounts.my_finance_account_ms.dto.RevenueDTO;
import br.com.myaccounts.my_finance_account_ms.dto.UpdateRevenueDTO;
import br.com.myaccounts.my_finance_account_ms.service.RevenueService;
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
@RequestMapping("/api/users/{userId}/revenues")
@RequiredArgsConstructor
@Slf4j
public class RevenueController implements RevenueControllerDoc {

    private final RevenueService revenueService;

    @Override
    @GetMapping
    public ResponseEntity<List<RevenueDTO>> getRevenues(
            @PathVariable UUID userId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        log.debug("GET /api/users/{}/revenues?startDate={}&endDate={}", userId, startDate, endDate);
        
        List<RevenueDTO> revenues;
        if (startDate != null && endDate != null) {
            revenues = revenueService.findByUserAndPeriod(userId, startDate, endDate);
        } else {
            revenues = revenueService.findByUser(userId);
        }
        
        return ResponseEntity.ok(revenues);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<RevenueDTO> getRevenue(@PathVariable UUID id, @PathVariable UUID userId) {
        log.debug("GET /api/users/{}/revenues/{}", userId, id);
        RevenueDTO revenue = revenueService.findById(id, userId);
        return ResponseEntity.ok(revenue);
    }

    @Override
    @PostMapping
    public ResponseEntity<RevenueDTO> createRevenue(
            @PathVariable UUID userId, 
            @Valid @RequestBody CreateRevenueDTO dto) {
        log.debug("POST /api/users/{}/revenues", userId);
        RevenueDTO revenue = revenueService.create(userId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(revenue);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<RevenueDTO> updateRevenue(
            @PathVariable UUID id, 
            @PathVariable UUID userId, 
            @Valid @RequestBody UpdateRevenueDTO dto) {
        log.debug("PUT /api/users/{}/revenues/{}", userId, id);
        RevenueDTO revenue = revenueService.update(id, userId, dto);
        return ResponseEntity.ok(revenue);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRevenue(@PathVariable UUID id, @PathVariable UUID userId) {
        log.debug("DELETE /api/users/{}/revenues/{}", userId, id);
        revenueService.delete(id, userId);
        return ResponseEntity.noContent().build();
    }

    @Override
    @GetMapping("/total")
    public ResponseEntity<BigDecimal> getTotalByPeriod(
            @PathVariable UUID userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        log.debug("GET /api/users/{}/revenues/total?startDate={}&endDate={}", userId, startDate, endDate);
        BigDecimal total = revenueService.calculateTotalByPeriod(userId, startDate, endDate);
        return ResponseEntity.ok(total);
    }

    @Override
    @GetMapping("/count")
    public ResponseEntity<Long> getRevenueCount(@PathVariable UUID userId) {
        log.debug("GET /api/users/{}/revenues/count", userId);
        Long count = revenueService.getRevenueCount(userId);
        return ResponseEntity.ok(count);
    }

    @Override
    @GetMapping("/recent")
    public ResponseEntity<List<RevenueDTO>> getRecentRevenues(@PathVariable UUID userId) {
        log.debug("GET /api/users/{}/revenues/recent", userId);
        List<RevenueDTO> revenues = revenueService.getRecentRevenues(userId);
        return ResponseEntity.ok(revenues);
    }
}

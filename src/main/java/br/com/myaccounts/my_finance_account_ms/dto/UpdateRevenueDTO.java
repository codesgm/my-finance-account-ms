package br.com.myaccounts.my_finance_account_ms.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateRevenueDTO {

    @Size(min = 2, max = 255, message = "Description must be between 2 and 255 characters")
    private String description;

    @Positive(message = "Amount must be positive")
    @DecimalMin(value = "0.01", message = "Amount must be at least 0.01")
    private BigDecimal amount;

    @PastOrPresent(message = "Date cannot be in the future")
    private LocalDate date;

    private UUID categoryId;

    public boolean isValid() {
        return (description == null || (!description.trim().isEmpty() && description.length() >= 2)) &&
               (amount == null || amount.compareTo(BigDecimal.ZERO) > 0) &&
               (date == null || !date.isAfter(LocalDate.now()));
    }
}

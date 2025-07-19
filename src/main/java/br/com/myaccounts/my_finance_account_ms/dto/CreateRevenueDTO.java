package br.com.myaccounts.my_finance_account_ms.dto;

import br.com.myaccounts.my_finance_account_ms.entity.Category;
import br.com.myaccounts.my_finance_account_ms.entity.Revenue;
import br.com.myaccounts.my_finance_account_ms.entity.User;
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
public class CreateRevenueDTO {

    @NotBlank(message = "Description is required")
    @Size(min = 2, max = 255, message = "Description must be between 2 and 255 characters")
    private String description;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    @DecimalMin(value = "0.01", message = "Amount must be at least 0.01")
    private BigDecimal amount;

    @NotNull(message = "Date is required")
    @PastOrPresent(message = "Date cannot be in the future")
    private LocalDate date;

    @NotNull(message = "Category ID is required")
    private UUID categoryId;

    public Revenue toEntity(User user, Category category) {
        return Revenue.builder()
                .description(this.description)
                .amount(this.amount)
                .date(this.date)
                .user(user)
                .category(category)
                .build();
    }

    public boolean isValid() {
        return description != null && !description.trim().isEmpty() &&
               amount != null && amount.compareTo(BigDecimal.ZERO) > 0 &&
               date != null && !date.isAfter(LocalDate.now()) &&
               categoryId != null;
    }
}

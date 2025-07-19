package br.com.myaccounts.my_finance_account_ms.dto;

import br.com.myaccounts.my_finance_account_ms.entity.Revenue;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RevenueDTO {

    private UUID id;
    private String description;
    private BigDecimal amount;
    private LocalDate date;
    private LocalDateTime createdAt;
    private CategoryDTO category;

    public RevenueDTO(Revenue revenue) {
        this.id = revenue.getId();
        this.description = revenue.getDescription();
        this.amount = revenue.getAmount();
        this.date = revenue.getDate();
        this.createdAt = revenue.getCreatedAt();
        this.category = new CategoryDTO(revenue.getCategory());
    }
}

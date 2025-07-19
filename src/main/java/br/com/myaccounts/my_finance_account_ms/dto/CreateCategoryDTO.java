package br.com.myaccounts.my_finance_account_ms.dto;

import br.com.myaccounts.my_finance_account_ms.entity.Category;
import br.com.myaccounts.my_finance_account_ms.entity.User;
import br.com.myaccounts.my_finance_account_ms.enums.TransactionType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Dados para criação ou atualização de uma categoria")
public class CreateCategoryDTO {

    @Schema(description = "Nome da categoria", example = "Salário", required = true)
    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    @Schema(description = "Tipo da categoria", example = "REVENUE", required = true, allowableValues = {"REVENUE", "EXPENSE"})
    @NotNull(message = "Type is required")
    private TransactionType type;

    @Schema(description = "Ícone da categoria (emoji ou código)", example = "💰")
    @Size(max = 50, message = "Icon must not exceed 50 characters")
    private String icon;

    @Schema(description = "Cor da categoria em hexadecimal", example = "#4CAF50")
    @Size(max = 7, message = "Color must not exceed 7 characters")
    private String color;

    public Category toEntity(User user) {
        return Category.builder()
                .name(this.name)
                .type(this.type)
                .icon(this.icon)
                .color(this.color)
                .user(user)
                .build();
    }

    public boolean isValid() {
        return name != null && !name.trim().isEmpty() &&
               type != null;
    }
}

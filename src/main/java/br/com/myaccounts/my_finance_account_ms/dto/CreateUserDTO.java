package br.com.myaccounts.my_finance_account_ms.dto;

import br.com.myaccounts.my_finance_account_ms.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Dados para criação de um novo usuário")
public class CreateUserDTO {

    @Schema(description = "Nome completo do usuário", example = "João Silva", required = true)
    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 255, message = "Name must be between 2 and 255 characters")
    private String name;

    @Schema(description = "Email único do usuário", example = "joao.silva@email.com", required = true)
    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    @Size(max = 255, message = "Email must not exceed 255 characters")
    private String email;

    public User toEntity() {
        return User.builder()
                .name(this.name)
                .email(this.email)
                .build();
    }

    public boolean isValid() {
        return name != null && !name.trim().isEmpty() &&
               email != null && !email.trim().isEmpty() &&
               email.contains("@");
    }
}

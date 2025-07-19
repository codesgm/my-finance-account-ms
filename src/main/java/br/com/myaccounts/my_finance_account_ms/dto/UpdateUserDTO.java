package br.com.myaccounts.my_finance_account_ms.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateUserDTO {

    @Size(min = 2, max = 255, message = "Name must be between 2 and 255 characters")
    private String name;

    @Email(message = "Email must be valid")
    @Size(max = 255, message = "Email must not exceed 255 characters")
    private String email;

    public boolean isValid() {
        return (name == null || (!name.trim().isEmpty() && name.length() >= 2)) &&
               (email == null || (!email.trim().isEmpty() && email.contains("@")));
    }
}

package br.com.myaccounts.my_finance_account_ms.dto;

import br.com.myaccounts.my_finance_account_ms.entity.User;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserWithCategoriesDTO {

    private UUID id;
    private String name;
    private String email;
    private LocalDateTime createdAt;
    private List<CategoryDTO> categories;

    public UserWithCategoriesDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.createdAt = user.getCreatedAt();
        this.categories = user.getCategories().stream()
                .filter(category -> !category.isDeleted())
                .map(CategoryDTO::new)
                .collect(Collectors.toList());
    }
}

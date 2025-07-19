package br.com.myaccounts.my_finance_account_ms.controller;

import br.com.myaccounts.my_finance_account_ms.controller.doc.CategoryControllerDoc;
import br.com.myaccounts.my_finance_account_ms.dto.CategoryDTO;
import br.com.myaccounts.my_finance_account_ms.dto.CreateCategoryDTO;
import br.com.myaccounts.my_finance_account_ms.enums.TransactionType;
import br.com.myaccounts.my_finance_account_ms.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users/{userId}/categories")
@RequiredArgsConstructor
@Slf4j
public class CategoryController implements CategoryControllerDoc {

    private final CategoryService categoryService;

    @Override
    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getCategories(
            @PathVariable UUID userId,
            @RequestParam(required = false) TransactionType type) {
        log.debug("GET /api/users/{}/categories?type={}", userId, type);
        
        List<CategoryDTO> categories;
        if (type != null) {
            categories = categoryService.findByUserAndType(userId, type);
        } else {
            categories = categoryService.findByUser(userId);
        }
        
        return ResponseEntity.ok(categories);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategory(@PathVariable UUID id, @PathVariable UUID userId) {
        log.debug("GET /api/users/{}/categories/{}", userId, id);
        CategoryDTO category = categoryService.findById(id, userId);
        return ResponseEntity.ok(category);
    }

    @Override
    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(
            @PathVariable UUID userId, 
            @Valid @RequestBody CreateCategoryDTO dto) {
        log.debug("POST /api/users/{}/categories", userId);
        CategoryDTO category = categoryService.create(userId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(category);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(
            @PathVariable UUID id, 
            @PathVariable UUID userId, 
            @Valid @RequestBody CreateCategoryDTO dto) {
        log.debug("PUT /api/users/{}/categories/{}", userId, id);
        CategoryDTO category = categoryService.update(id, userId, dto);
        return ResponseEntity.ok(category);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable UUID id, @PathVariable UUID userId) {
        log.debug("DELETE /api/users/{}/categories/{}", userId, id);
        categoryService.delete(id, userId);
        return ResponseEntity.noContent().build();
    }

    @Override
    @GetMapping("/count")
    public ResponseEntity<Long> getCategoryCount(@PathVariable UUID userId) {
        log.debug("GET /api/users/{}/categories/count", userId);
        Long count = categoryService.getCategoryCount(userId);
        return ResponseEntity.ok(count);
    }

    @Override
    @PostMapping("/defaults")
    public ResponseEntity<List<CategoryDTO>> createDefaultCategories(@PathVariable UUID userId) {
        log.debug("POST /api/users/{}/categories/defaults", userId);
        List<CategoryDTO> categories = categoryService.createDefaultCategories(userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(categories);
    }
}

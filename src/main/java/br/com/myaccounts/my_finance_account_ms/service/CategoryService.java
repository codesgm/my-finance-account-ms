package br.com.myaccounts.my_finance_account_ms.service;

import br.com.myaccounts.my_finance_account_ms.dto.CategoryDTO;
import br.com.myaccounts.my_finance_account_ms.dto.CreateCategoryDTO;
import br.com.myaccounts.my_finance_account_ms.entity.Category;
import br.com.myaccounts.my_finance_account_ms.entity.User;
import br.com.myaccounts.my_finance_account_ms.enums.TransactionType;
import br.com.myaccounts.my_finance_account_ms.exception.CategoryNotFoundException;
import br.com.myaccounts.my_finance_account_ms.exception.ValidationException;
import br.com.myaccounts.my_finance_account_ms.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final UserService userService;

    @Transactional(readOnly = true)
    public List<CategoryDTO> findByUser(UUID userId) {
        log.debug("Finding categories for user ID: {}", userId);
        userService.validateUserExists(userId);
        return categoryRepository.findByUserId(userId).stream()
                .map(CategoryDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CategoryDTO> findByUserAndType(UUID userId, TransactionType type) {
        log.debug("Finding categories for user ID: {} and type: {}", userId, type);
        userService.validateUserExists(userId);
        return categoryRepository.findByUserIdAndType(userId, type).stream()
                .map(CategoryDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CategoryDTO findById(UUID id, UUID userId) {
        log.debug("Finding category by ID: {} for user: {}", id, userId);
        Category category = validateCategoryOwnership(id, userId);
        return new CategoryDTO(category);
    }

    public CategoryDTO create(UUID userId, CreateCategoryDTO dto) {
        log.debug("Creating new category for user ID: {}", userId);
        
        if (!dto.isValid()) {
            throw new ValidationException("Invalid category data provided");
        }

        User user = userService.validateUserExists(userId);
        
        Category category = dto.toEntity(user);
        Category savedCategory = categoryRepository.save(category);
        
        log.info("Category created successfully with ID: {} for user: {}", savedCategory.getId(), userId);
        return new CategoryDTO(savedCategory);
    }

    public CategoryDTO update(UUID id, UUID userId, CreateCategoryDTO dto) {
        log.debug("Updating category with ID: {} for user: {}", id, userId);
        
        if (!dto.isValid()) {
            throw new ValidationException("Invalid category data provided");
        }

        Category category = validateCategoryOwnership(id, userId);
        
        category.setName(dto.getName());
        category.setType(dto.getType());
        category.setIcon(dto.getIcon());
        category.setColor(dto.getColor());
        
        Category savedCategory = categoryRepository.save(category);
        log.info("Category updated successfully with ID: {} for user: {}", savedCategory.getId(), userId);
        return new CategoryDTO(savedCategory);
    }

    public void delete(UUID id, UUID userId) {
        log.debug("Deleting category with ID: {} for user: {}", id, userId);
        Category category = validateCategoryOwnership(id, userId);
        category.delete(); // Soft delete
        categoryRepository.save(category);
        log.info("Category soft deleted successfully with ID: {} for user: {}", id, userId);
    }

    @Transactional(readOnly = true)
    public Category validateCategoryOwnership(UUID categoryId, UUID userId) {
        return categoryRepository.findByIdAndUserId(categoryId, userId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));
    }

    @Transactional(readOnly = true)
    public Long getCategoryCount(UUID userId) {
        userService.validateUserExists(userId);
        return categoryRepository.countByUserId(userId);
    }

    @Transactional(readOnly = true)
    public Long getCategoryCountByType(UUID userId, TransactionType type) {
        userService.validateUserExists(userId);
        return categoryRepository.countByUserIdAndType(userId, type);
    }

    public List<CategoryDTO> createDefaultCategories(UUID userId) {
        log.debug("Creating default categories for user ID: {}", userId);
        User user = userService.validateUserExists(userId);
        
        List<Category> defaultCategories = new ArrayList<>();
        
        // Default Revenue Categories
        defaultCategories.add(Category.builder()
                .name("Salary")
                .type(TransactionType.REVENUE)
                .icon("💰")
                .color("#4CAF50")
                .user(user)
                .build());
                
        defaultCategories.add(Category.builder()
                .name("Freelance")
                .type(TransactionType.REVENUE)
                .icon("💻")
                .color("#2196F3")
                .user(user)
                .build());
                
        defaultCategories.add(Category.builder()
                .name("Investment")
                .type(TransactionType.REVENUE)
                .icon("📈")
                .color("#FF9800")
                .user(user)
                .build());
        
        // Default Expense Categories
        defaultCategories.add(Category.builder()
                .name("Food")
                .type(TransactionType.EXPENSE)
                .icon("🍽️")
                .color("#FF5722")
                .user(user)
                .build());
                
        defaultCategories.add(Category.builder()
                .name("Transportation")
                .type(TransactionType.EXPENSE)
                .icon("🚗")
                .color("#607D8B")
                .user(user)
                .build());
                
        defaultCategories.add(Category.builder()
                .name("Entertainment")
                .type(TransactionType.EXPENSE)
                .icon("🎬")
                .color("#9C27B0")
                .user(user)
                .build());
                
        defaultCategories.add(Category.builder()
                .name("Health")
                .type(TransactionType.EXPENSE)
                .icon("🏥")
                .color("#F44336")
                .user(user)
                .build());
        
        List<Category> savedCategories = categoryRepository.saveAll(defaultCategories);
        log.info("Created {} default categories for user: {}", savedCategories.size(), userId);
        
        return savedCategories.stream()
                .map(CategoryDTO::new)
                .collect(Collectors.toList());
    }
}

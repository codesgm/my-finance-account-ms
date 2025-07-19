package br.com.myaccounts.my_finance_account_ms.service;

import br.com.myaccounts.my_finance_account_ms.dto.CreateExpenseDTO;
import br.com.myaccounts.my_finance_account_ms.dto.ExpenseDTO;
import br.com.myaccounts.my_finance_account_ms.dto.UpdateExpenseDTO;
import br.com.myaccounts.my_finance_account_ms.entity.Category;
import br.com.myaccounts.my_finance_account_ms.entity.Expense;
import br.com.myaccounts.my_finance_account_ms.entity.User;
import br.com.myaccounts.my_finance_account_ms.exception.ExpenseNotFoundException;
import br.com.myaccounts.my_finance_account_ms.exception.ValidationException;
import br.com.myaccounts.my_finance_account_ms.repository.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final UserService userService;
    private final CategoryService categoryService;

    @Transactional(readOnly = true)
    public List<ExpenseDTO> findByUser(UUID userId) {
        log.debug("Finding expenses for user ID: {}", userId);
        userService.validateUserExists(userId);
        return expenseRepository.findByUserId(userId).stream()
                .map(ExpenseDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ExpenseDTO> findByUserAndPeriod(UUID userId, LocalDate startDate, LocalDate endDate) {
        log.debug("Finding expenses for user ID: {} between {} and {}", userId, startDate, endDate);
        userService.validateUserExists(userId);
        
        if (startDate.isAfter(endDate)) {
            throw new ValidationException("Start date cannot be after end date");
        }
        
        return expenseRepository.findByUserIdAndDateBetween(userId, startDate, endDate).stream()
                .map(ExpenseDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ExpenseDTO> findByUserAndCategory(UUID userId, UUID categoryId) {
        log.debug("Finding expenses for user ID: {} and category ID: {}", userId, categoryId);
        userService.validateUserExists(userId);
        categoryService.validateCategoryOwnership(categoryId, userId);
        
        return expenseRepository.findByUserIdAndCategoryId(userId, categoryId).stream()
                .map(ExpenseDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ExpenseDTO findById(UUID id, UUID userId) {
        log.debug("Finding expense by ID: {} for user: {}", id, userId);
        Expense expense = validateExpenseOwnership(id, userId);
        return new ExpenseDTO(expense);
    }

    public ExpenseDTO create(UUID userId, CreateExpenseDTO dto) {
        log.debug("Creating new expense for user ID: {}", userId);
        
        if (!dto.isValid()) {
            throw new ValidationException("Invalid expense data provided");
        }

        User user = userService.validateUserExists(userId);
        Category category = categoryService.validateCategoryOwnership(dto.getCategoryId(), userId);
        
        // Validate category type
        if (!category.isExpense()) {
            throw new ValidationException("Category must be of type EXPENSE");
        }
        
        Expense expense = dto.toEntity(user, category);
        Expense savedExpense = expenseRepository.save(expense);
        
        log.info("Expense created successfully with ID: {} for user: {}", savedExpense.getId(), userId);
        return new ExpenseDTO(savedExpense);
    }

    public ExpenseDTO update(UUID id, UUID userId, UpdateExpenseDTO dto) {
        log.debug("Updating expense with ID: {} for user: {}", id, userId);
        
        if (!dto.isValid()) {
            throw new ValidationException("Invalid expense data provided");
        }

        Expense expense = validateExpenseOwnership(id, userId);
        
        if (dto.getDescription() != null) {
            expense.setDescription(dto.getDescription());
        }
        if (dto.getAmount() != null) {
            expense.setAmount(dto.getAmount());
        }
        if (dto.getDate() != null) {
            expense.setDate(dto.getDate());
        }
        if (dto.getCategoryId() != null) {
            Category category = categoryService.validateCategoryOwnership(dto.getCategoryId(), userId);
            if (!category.isExpense()) {
                throw new ValidationException("Category must be of type EXPENSE");
            }
            expense.setCategory(category);
        }
        
        Expense savedExpense = expenseRepository.save(expense);
        log.info("Expense updated successfully with ID: {} for user: {}", savedExpense.getId(), userId);
        return new ExpenseDTO(savedExpense);
    }

    public void delete(UUID id, UUID userId) {
        log.debug("Deleting expense with ID: {} for user: {}", id, userId);
        Expense expense = validateExpenseOwnership(id, userId);
        expense.delete(); // Soft delete
        expenseRepository.save(expense);
        log.info("Expense soft deleted successfully with ID: {} for user: {}", id, userId);
    }

    @Transactional(readOnly = true)
    public BigDecimal calculateTotalByPeriod(UUID userId, LocalDate startDate, LocalDate endDate) {
        log.debug("Calculating total expenses for user ID: {} between {} and {}", userId, startDate, endDate);
        userService.validateUserExists(userId);
        
        if (startDate.isAfter(endDate)) {
            throw new ValidationException("Start date cannot be after end date");
        }
        
        return expenseRepository.sumByUserIdAndDateBetween(userId, startDate, endDate);
    }

    @Transactional(readOnly = true)
    public Long getExpenseCount(UUID userId) {
        userService.validateUserExists(userId);
        return expenseRepository.countByUserId(userId);
    }

    @Transactional(readOnly = true)
    public List<ExpenseDTO> getRecentExpenses(UUID userId) {
        log.debug("Getting recent expenses for user ID: {}", userId);
        userService.validateUserExists(userId);
        return expenseRepository.findTop10ByUserIdOrderByDateDesc(userId).stream()
                .map(ExpenseDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Expense validateExpenseOwnership(UUID expenseId, UUID userId) {
        return expenseRepository.findByIdAndUserId(expenseId, userId)
                .orElseThrow(() -> new ExpenseNotFoundException(expenseId));
    }
}

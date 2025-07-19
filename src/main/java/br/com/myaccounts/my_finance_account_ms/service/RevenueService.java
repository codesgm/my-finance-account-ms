package br.com.myaccounts.my_finance_account_ms.service;

import br.com.myaccounts.my_finance_account_ms.dto.CreateRevenueDTO;
import br.com.myaccounts.my_finance_account_ms.dto.RevenueDTO;
import br.com.myaccounts.my_finance_account_ms.dto.UpdateRevenueDTO;
import br.com.myaccounts.my_finance_account_ms.entity.Category;
import br.com.myaccounts.my_finance_account_ms.entity.Revenue;
import br.com.myaccounts.my_finance_account_ms.entity.User;
import br.com.myaccounts.my_finance_account_ms.enums.TransactionType;
import br.com.myaccounts.my_finance_account_ms.exception.RevenueNotFoundException;
import br.com.myaccounts.my_finance_account_ms.exception.ValidationException;
import br.com.myaccounts.my_finance_account_ms.repository.RevenueRepository;
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
public class RevenueService {

    private final RevenueRepository revenueRepository;
    private final UserService userService;
    private final CategoryService categoryService;

    @Transactional(readOnly = true)
    public List<RevenueDTO> findByUser(UUID userId) {
        log.debug("Finding revenues for user ID: {}", userId);
        userService.validateUserExists(userId);
        return revenueRepository.findByUserId(userId).stream()
                .map(RevenueDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<RevenueDTO> findByUserAndPeriod(UUID userId, LocalDate startDate, LocalDate endDate) {
        log.debug("Finding revenues for user ID: {} between {} and {}", userId, startDate, endDate);
        userService.validateUserExists(userId);
        
        if (startDate.isAfter(endDate)) {
            throw new ValidationException("Start date cannot be after end date");
        }
        
        return revenueRepository.findByUserIdAndDateBetween(userId, startDate, endDate).stream()
                .map(RevenueDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<RevenueDTO> findByUserAndCategory(UUID userId, UUID categoryId) {
        log.debug("Finding revenues for user ID: {} and category ID: {}", userId, categoryId);
        userService.validateUserExists(userId);
        categoryService.validateCategoryOwnership(categoryId, userId);
        
        return revenueRepository.findByUserIdAndCategoryId(userId, categoryId).stream()
                .map(RevenueDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public RevenueDTO findById(UUID id, UUID userId) {
        log.debug("Finding revenue by ID: {} for user: {}", id, userId);
        Revenue revenue = validateRevenueOwnership(id, userId);
        return new RevenueDTO(revenue);
    }

    public RevenueDTO create(UUID userId, CreateRevenueDTO dto) {
        log.debug("Creating new revenue for user ID: {}", userId);
        
        if (!dto.isValid()) {
            throw new ValidationException("Invalid revenue data provided");
        }

        User user = userService.validateUserExists(userId);
        Category category = categoryService.validateCategoryOwnership(dto.getCategoryId(), userId);
        
        // Validate category type
        if (!category.isRevenue()) {
            throw new ValidationException("Category must be of type REVENUE");
        }
        
        Revenue revenue = dto.toEntity(user, category);
        Revenue savedRevenue = revenueRepository.save(revenue);
        
        log.info("Revenue created successfully with ID: {} for user: {}", savedRevenue.getId(), userId);
        return new RevenueDTO(savedRevenue);
    }

    public RevenueDTO update(UUID id, UUID userId, UpdateRevenueDTO dto) {
        log.debug("Updating revenue with ID: {} for user: {}", id, userId);
        
        if (!dto.isValid()) {
            throw new ValidationException("Invalid revenue data provided");
        }

        Revenue revenue = validateRevenueOwnership(id, userId);
        
        if (dto.getDescription() != null) {
            revenue.setDescription(dto.getDescription());
        }
        if (dto.getAmount() != null) {
            revenue.setAmount(dto.getAmount());
        }
        if (dto.getDate() != null) {
            revenue.setDate(dto.getDate());
        }
        if (dto.getCategoryId() != null) {
            Category category = categoryService.validateCategoryOwnership(dto.getCategoryId(), userId);
            if (!category.isRevenue()) {
                throw new ValidationException("Category must be of type REVENUE");
            }
            revenue.setCategory(category);
        }
        
        Revenue savedRevenue = revenueRepository.save(revenue);
        log.info("Revenue updated successfully with ID: {} for user: {}", savedRevenue.getId(), userId);
        return new RevenueDTO(savedRevenue);
    }

    public void delete(UUID id, UUID userId) {
        log.debug("Deleting revenue with ID: {} for user: {}", id, userId);
        Revenue revenue = validateRevenueOwnership(id, userId);
        revenue.delete(); // Soft delete
        revenueRepository.save(revenue);
        log.info("Revenue soft deleted successfully with ID: {} for user: {}", id, userId);
    }

    @Transactional(readOnly = true)
    public BigDecimal calculateTotalByPeriod(UUID userId, LocalDate startDate, LocalDate endDate) {
        log.debug("Calculating total revenues for user ID: {} between {} and {}", userId, startDate, endDate);
        userService.validateUserExists(userId);
        
        if (startDate.isAfter(endDate)) {
            throw new ValidationException("Start date cannot be after end date");
        }
        
        return revenueRepository.sumByUserIdAndDateBetween(userId, startDate, endDate);
    }

    @Transactional(readOnly = true)
    public Long getRevenueCount(UUID userId) {
        userService.validateUserExists(userId);
        return revenueRepository.countByUserId(userId);
    }

    @Transactional(readOnly = true)
    public List<RevenueDTO> getRecentRevenues(UUID userId) {
        log.debug("Getting recent revenues for user ID: {}", userId);
        userService.validateUserExists(userId);
        return revenueRepository.findTop10ByUserIdOrderByDateDesc(userId).stream()
                .map(RevenueDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Revenue validateRevenueOwnership(UUID revenueId, UUID userId) {
        return revenueRepository.findByIdAndUserId(revenueId, userId)
                .orElseThrow(() -> new RevenueNotFoundException(revenueId));
    }
}

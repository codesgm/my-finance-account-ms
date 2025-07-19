package br.com.myaccounts.my_finance_account_ms.repository;

import br.com.myaccounts.my_finance_account_ms.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, UUID> {

    @Query("SELECT e FROM Expense e WHERE e.user.id = :userId AND e.deletedAt IS NULL ORDER BY e.date DESC")
    List<Expense> findByUserId(@Param("userId") UUID userId);

    @Query("SELECT e FROM Expense e WHERE e.user.id = :userId AND e.deletedAt IS NULL ORDER BY e.date DESC")
    List<Expense> findByUserIdOrderByDateDesc(@Param("userId") UUID userId);

    @Query("SELECT e FROM Expense e WHERE e.user.id = :userId AND e.date BETWEEN :startDate AND :endDate AND e.deletedAt IS NULL ORDER BY e.date DESC")
    List<Expense> findByUserIdAndDateBetween(@Param("userId") UUID userId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT e FROM Expense e WHERE e.user.id = :userId AND e.category.id = :categoryId AND e.deletedAt IS NULL ORDER BY e.date DESC")
    List<Expense> findByUserIdAndCategoryId(@Param("userId") UUID userId, @Param("categoryId") UUID categoryId);

    @Query("SELECT e FROM Expense e WHERE e.id = :id AND e.user.id = :userId AND e.deletedAt IS NULL")
    Optional<Expense> findByIdAndUserId(@Param("id") UUID id, @Param("userId") UUID userId);

    @Query("SELECT COALESCE(SUM(e.amount), 0) FROM Expense e WHERE e.user.id = :userId AND e.date BETWEEN :startDate AND :endDate AND e.deletedAt IS NULL")
    BigDecimal sumByUserIdAndDateBetween(@Param("userId") UUID userId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT COUNT(e) FROM Expense e WHERE e.user.id = :userId AND e.deletedAt IS NULL")
    Long countByUserId(@Param("userId") UUID userId);

    @Query("SELECT COUNT(e) FROM Expense e WHERE e.user.id = :userId AND e.date BETWEEN :startDate AND :endDate AND e.deletedAt IS NULL")
    Long countByUserIdAndDateBetween(@Param("userId") UUID userId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END FROM Expense e WHERE e.id = :id AND e.user.id = :userId AND e.deletedAt IS NULL")
    boolean existsByIdAndUserId(@Param("id") UUID id, @Param("userId") UUID userId);

    @Query("SELECT e FROM Expense e WHERE e.user.id = :userId AND e.deletedAt IS NULL ORDER BY e.date DESC LIMIT 10")
    List<Expense> findTop10ByUserIdOrderByDateDesc(@Param("userId") UUID userId);
}

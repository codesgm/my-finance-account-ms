package br.com.myaccounts.my_finance_account_ms.repository;

import br.com.myaccounts.my_finance_account_ms.entity.Revenue;
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
public interface RevenueRepository extends JpaRepository<Revenue, UUID> {

    @Query("SELECT r FROM Revenue r WHERE r.user.id = :userId AND r.deletedAt IS NULL ORDER BY r.date DESC")
    List<Revenue> findByUserId(@Param("userId") UUID userId);

    @Query("SELECT r FROM Revenue r WHERE r.user.id = :userId AND r.deletedAt IS NULL ORDER BY r.date DESC")
    List<Revenue> findByUserIdOrderByDateDesc(@Param("userId") UUID userId);

    @Query("SELECT r FROM Revenue r WHERE r.user.id = :userId AND r.date BETWEEN :startDate AND :endDate AND r.deletedAt IS NULL ORDER BY r.date DESC")
    List<Revenue> findByUserIdAndDateBetween(@Param("userId") UUID userId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT r FROM Revenue r WHERE r.user.id = :userId AND r.category.id = :categoryId AND r.deletedAt IS NULL ORDER BY r.date DESC")
    List<Revenue> findByUserIdAndCategoryId(@Param("userId") UUID userId, @Param("categoryId") UUID categoryId);

    @Query("SELECT r FROM Revenue r WHERE r.id = :id AND r.user.id = :userId AND r.deletedAt IS NULL")
    Optional<Revenue> findByIdAndUserId(@Param("id") UUID id, @Param("userId") UUID userId);

    @Query("SELECT COALESCE(SUM(r.amount), 0) FROM Revenue r WHERE r.user.id = :userId AND r.date BETWEEN :startDate AND :endDate AND r.deletedAt IS NULL")
    BigDecimal sumByUserIdAndDateBetween(@Param("userId") UUID userId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT COUNT(r) FROM Revenue r WHERE r.user.id = :userId AND r.deletedAt IS NULL")
    Long countByUserId(@Param("userId") UUID userId);

    @Query("SELECT COUNT(r) FROM Revenue r WHERE r.user.id = :userId AND r.date BETWEEN :startDate AND :endDate AND r.deletedAt IS NULL")
    Long countByUserIdAndDateBetween(@Param("userId") UUID userId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END FROM Revenue r WHERE r.id = :id AND r.user.id = :userId AND r.deletedAt IS NULL")
    boolean existsByIdAndUserId(@Param("id") UUID id, @Param("userId") UUID userId);

    @Query("SELECT r FROM Revenue r WHERE r.user.id = :userId AND r.deletedAt IS NULL ORDER BY r.date DESC LIMIT 10")
    List<Revenue> findTop10ByUserIdOrderByDateDesc(@Param("userId") UUID userId);
}

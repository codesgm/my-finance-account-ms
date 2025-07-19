package br.com.myaccounts.my_finance_account_ms.repository;

import br.com.myaccounts.my_finance_account_ms.entity.Category;
import br.com.myaccounts.my_finance_account_ms.enums.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {

    @Query("SELECT c FROM Category c WHERE c.user.id = :userId AND c.deletedAt IS NULL ORDER BY c.name")
    List<Category> findByUserId(@Param("userId") UUID userId);

    @Query("SELECT c FROM Category c WHERE c.user.id = :userId AND c.type = :type AND c.deletedAt IS NULL ORDER BY c.name")
    List<Category> findByUserIdAndType(@Param("userId") UUID userId, @Param("type") TransactionType type);

    @Query("SELECT c FROM Category c WHERE c.id = :id AND c.user.id = :userId AND c.deletedAt IS NULL")
    Optional<Category> findByIdAndUserId(@Param("id") UUID id, @Param("userId") UUID userId);

    @Query("SELECT COUNT(c) FROM Category c WHERE c.user.id = :userId AND c.deletedAt IS NULL")
    Long countByUserId(@Param("userId") UUID userId);

    @Query("SELECT COUNT(c) FROM Category c WHERE c.user.id = :userId AND c.type = :type AND c.deletedAt IS NULL")
    Long countByUserIdAndType(@Param("userId") UUID userId, @Param("type") TransactionType type);

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Category c WHERE c.id = :id AND c.user.id = :userId AND c.deletedAt IS NULL")
    boolean existsByIdAndUserId(@Param("id") UUID id, @Param("userId") UUID userId);

    @Query("SELECT c FROM Category c WHERE c.user.id = :userId AND c.deletedAt IS NULL ORDER BY c.name")
    List<Category> findByUserIdOrderByName(@Param("userId") UUID userId);
}

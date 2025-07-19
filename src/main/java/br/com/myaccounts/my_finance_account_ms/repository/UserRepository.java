package br.com.myaccounts.my_finance_account_ms.repository;

import br.com.myaccounts.my_finance_account_ms.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    @Query("SELECT u FROM User u WHERE u.id = :id AND u.deletedAt IS NULL")
    Optional<User> findById(@Param("id") UUID id);

    @Query("SELECT u FROM User u WHERE u.email = :email AND u.deletedAt IS NULL")
    Optional<User> findByEmail(@Param("email") String email);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.categories c WHERE u.id = :id AND u.deletedAt IS NULL AND (c.deletedAt IS NULL OR c IS NULL)")
    Optional<User> findByIdWithCategories(@Param("id") UUID id);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.revenues r WHERE u.id = :id AND u.deletedAt IS NULL AND (r.deletedAt IS NULL OR r IS NULL)")
    Optional<User> findByIdWithRevenues(@Param("id") UUID id);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.expenses e WHERE u.id = :id AND u.deletedAt IS NULL AND (e.deletedAt IS NULL OR e IS NULL)")
    Optional<User> findByIdWithExpenses(@Param("id") UUID id);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.email = :email AND u.deletedAt IS NULL")
    boolean existsByEmail(@Param("email") String email);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.id = :id AND u.deletedAt IS NULL")
    boolean existsById(@Param("id") UUID id);

    @Query("SELECT COUNT(u) FROM User u WHERE u.deletedAt IS NULL")
    long count();

    @Query("SELECT u FROM User u WHERE u.deletedAt IS NULL ORDER BY u.createdAt DESC")
    List<User> findAll();
}

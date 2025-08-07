package com.lushaj.ExpenseBackend.repository;

import com.lushaj.ExpenseBackend.entity.ExpenseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExpenseRepository extends JpaRepository<ExpenseEntity, Long> {

    Optional<ExpenseEntity> findByExpenseId(String expenseId);

    List<ExpenseEntity> findByOwnerId(Long id);

    Optional<ExpenseEntity> findByOwnerIdAndExpenseId(Long id, String expenseId);
}

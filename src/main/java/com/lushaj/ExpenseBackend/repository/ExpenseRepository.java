package com.lushaj.ExpenseBackend.repository;

import com.lushaj.ExpenseBackend.entity.ExpenseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<ExpenseEntity, Long> {

}

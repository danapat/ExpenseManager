package com.lushaj.ExpenseBackend.service;

import com.lushaj.ExpenseBackend.dto.ExpenseDTO;

import java.util.List;

public interface ExpenseService {

    List<ExpenseDTO> getAllExpenses();

    ExpenseDTO getExpenseByExpenseId(String expenseId);

    void deleteExpenseByExpenseId(String expenseId);
}

package com.lushaj.ExpenseBackend.service.impl;

import com.lushaj.ExpenseBackend.dto.ExpenseDTO;
import com.lushaj.ExpenseBackend.entity.ExpenseEntity;
import com.lushaj.ExpenseBackend.exceptions.ResourceNotFoundException;
import com.lushaj.ExpenseBackend.repository.ExpenseRepository;
import com.lushaj.ExpenseBackend.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<ExpenseDTO> getAllExpenses() {
        // call the repository method
        List<ExpenseEntity> list = expenseRepository.findAll();
        log.info("Printing data from repository: {}", list);
        // convert the Entity object to DTO object
        List<ExpenseDTO> listOfExpenses = list.stream().map(expenseEntity -> mapToExpenseDTO(expenseEntity)).collect(Collectors.toList());
        // return the list
        return listOfExpenses;
    }

    @Override
    public ExpenseDTO getExpenseByExpenseId(String expenseId) {
        ExpenseEntity optionalExpense = expenseRepository.findByExpenseId(expenseId)
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found with id: " + expenseId));
        return mapToExpenseDTO(optionalExpense);
    }

    private ExpenseDTO mapToExpenseDTO(ExpenseEntity expenseEntity) {
        return modelMapper.map(expenseEntity, ExpenseDTO.class);
    }

    @Override
    public void deleteExpenseByExpenseId(String expenseId) {
        // Optional: check if expense exists, sonst Exception werfen
        ExpenseEntity entity = expenseRepository.findByExpenseId(expenseId)
                .orElseThrow(() -> new RuntimeException("Expense not found"));

        expenseRepository.delete(entity);
    }
}

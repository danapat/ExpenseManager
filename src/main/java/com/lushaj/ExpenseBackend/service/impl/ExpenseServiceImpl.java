package com.lushaj.ExpenseBackend.service.impl;

import com.lushaj.ExpenseBackend.dto.ExpenseDTO;
import com.lushaj.ExpenseBackend.entity.ExpenseEntity;
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

    private ExpenseDTO mapToExpenseDTO(ExpenseEntity expenseEntity) {
        return modelMapper.map(expenseEntity, ExpenseDTO.class);
    }
}

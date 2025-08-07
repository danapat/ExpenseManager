package com.lushaj.ExpenseBackend.service.impl;

import com.lushaj.ExpenseBackend.dto.ExpenseDTO;
import com.lushaj.ExpenseBackend.entity.ExpenseEntity;
import com.lushaj.ExpenseBackend.entity.ProfileEntity;
import com.lushaj.ExpenseBackend.exceptions.ResourceNotFoundException;
import com.lushaj.ExpenseBackend.repository.ExpenseRepository;
import com.lushaj.ExpenseBackend.service.AuthService;
import com.lushaj.ExpenseBackend.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;

    private final ModelMapper modelMapper;

    private final AuthService authService;

    @Override
    public List<ExpenseDTO> getAllExpenses() {
        Long loggedInProfileId = authService.getLoggedInProfile().getId();
        List<ExpenseEntity> list = expenseRepository.findByOwnerId(loggedInProfileId);
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

    @Override
    public ExpenseDTO saveExpenseDetails(ExpenseDTO expenseDTO) {
        ProfileEntity profileEntity = authService.getLoggedInProfile();
        ExpenseEntity newExpenseEntity = mapToExpenseEntity(expenseDTO);
        newExpenseEntity.setExpenseId(UUID.randomUUID().toString());
        newExpenseEntity.setOwner(profileEntity);
        newExpenseEntity = expenseRepository.save(newExpenseEntity);
        return mapToExpenseDTO(newExpenseEntity);
    }

    @Override
    public ExpenseDTO updateExpenseDetails(ExpenseDTO expenseDTO, String expenseId) {
        ExpenseEntity existingExpense = getExpenseEntity(expenseId);
        ExpenseEntity updatedExpenseEntity = mapToExpenseEntity(expenseDTO);
        updatedExpenseEntity.setId(existingExpense.getId());
        updatedExpenseEntity.setExpenseId(existingExpense.getExpenseId());
        updatedExpenseEntity.setCreatedAt(existingExpense.getCreatedAt());
        updatedExpenseEntity.setUpdatedAt(existingExpense.getUpdatedAt());
        updatedExpenseEntity.setOwner(authService.getLoggedInProfile());
        updatedExpenseEntity = expenseRepository.save(updatedExpenseEntity);
        log.info("Printing data from repository: {}", updatedExpenseEntity);
        return mapToExpenseDTO(updatedExpenseEntity);
    }

    private ExpenseEntity getExpenseEntity(String expenseId) {
        Long id = authService.getLoggedInProfile().getId();
        return expenseRepository.findByOwnerIdAndExpenseId(id, expenseId)
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found with id: " + expenseId));
    }

    private ExpenseEntity mapToExpenseEntity(ExpenseDTO expenseDTO) {
        return modelMapper.map(expenseDTO, ExpenseEntity.class);
    }
}

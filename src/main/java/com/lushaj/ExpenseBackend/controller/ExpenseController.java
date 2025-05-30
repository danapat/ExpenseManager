package com.lushaj.ExpenseBackend.controller;

import com.lushaj.ExpenseBackend.dto.ExpenseDTO;
import com.lushaj.ExpenseBackend.io.ExpenseResponse;
import com.lushaj.ExpenseBackend.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * this is controller class for Expense module
 * @author or
 */
@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin("*")
public class ExpenseController {

    private final ExpenseService expenseService;
    private final ModelMapper modelMapper;

    /**
     * it will fetch the expenses from database
     * @return list
     */
    @GetMapping("/expenses")
    public List<ExpenseResponse> getExpenses(){
        log.info("API GET /expenses called");
        //call the service method
        List<ExpenseDTO> list = expenseService.getAllExpenses();
        log.info("Printing data from service {}", list);
        //convert the Expense DTO to Expense Response
        List<ExpenseResponse> response = list
                .stream()
                .map(expenseDTO ->
                 mapToExpenseResponse(expenseDTO))
                .collect(Collectors.toList());
        //return the list/response
        return response;
    }

/**
 * Mapper method for converting expense dto object to expense response
 * @param expenseDTO
 * @return ExpenseResponse
 * */

    private ExpenseResponse mapToExpenseResponse(ExpenseDTO expenseDTO) {
        return modelMapper.map(expenseDTO, ExpenseResponse.class);
    }
}

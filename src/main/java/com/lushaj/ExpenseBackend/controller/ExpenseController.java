package com.lushaj.ExpenseBackend.controller;

import com.lushaj.ExpenseBackend.dto.ExpenseDTO;
import com.lushaj.ExpenseBackend.io.ExpenseResponse;
import com.lushaj.ExpenseBackend.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/expenses/{expenseId}")
    public ExpenseResponse getExpenseById(@PathVariable String expenseId) {
        log.info("Printing the expense id {}", expenseId);
        ExpenseDTO expenseDTO = expenseService.getExpenseByExpenseId(expenseId);
        return mapToExpenseResponse(expenseDTO);
    }

    @DeleteMapping("/expenses/{expenseId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteExpenseById(@PathVariable String expenseId) {
        log.info("API DELETE /expenses/{} called", expenseId);
        expenseService.deleteExpenseByExpenseId(expenseId);
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

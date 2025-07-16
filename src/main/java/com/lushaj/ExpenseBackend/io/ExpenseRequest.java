package com.lushaj.ExpenseBackend.io;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExpenseRequest {

    @NotBlank (message = "Expense name is required")
    @Size(min = 3)
    @Pattern(regexp = "^[a-zA-ZÄÖÜäöüß\\s]+$")
    private String name;

    private String note;

    @NotBlank (message = "Expense category is required")
    @Size(min = 3)
    @Pattern(regexp = "^[a-zA-ZÄÖÜäöüß\\s]+$")
    private String category;

    @NotNull (message = "Date is required")
    private Date date;

    @NotNull (message = "Amount is required")
    private BigDecimal amount;
}

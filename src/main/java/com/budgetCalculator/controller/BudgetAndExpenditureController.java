package com.budgetCalculator.controller;

import com.budgetCalculator.entity.Budget;
import com.budgetCalculator.entity.Expenditure;
import com.budgetCalculator.exception.ApiRequestException;
import com.budgetCalculator.services.BudgetServiceImp;
import com.budgetCalculator.services.ExpenditureServiceImp;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class BudgetAndExpenditureController {

    private final BudgetServiceImp budgetServiceImp;
    private final ExpenditureServiceImp expenditureServiceImp;

    @GetMapping("/hello")
    public String getHello() {
        return "Hello World";
    }

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List<Budget> saveBudget(@RequestBody Budget request) {
        this.budgetServiceImp.save(request);
        return this.budgetServiceImp.getAllBudgets();
    }

    @PostMapping(value = "/expenditure/save", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public List<Expenditure> saveExpenditure(@RequestBody Expenditure expenditure) {
        this.expenditureServiceImp.expenditureSave(expenditure);
        return expenditureServiceImp.getAllExpenditures();
    }

    @PostMapping("/getBudgetById")
    public Budget getBudgetById(@RequestBody Integer id) {
        return budgetServiceImp.findBudgetbyId(id);
    }

    @GetMapping("/getAllBudgets")
    public List<Budget> getAllBudgets() {
        if (this.expenditureServiceImp.getAllExpenditures().isEmpty()) {
            throw new ApiRequestException("Opps cannot get all Students with custom exception");
        }
        return this.budgetServiceImp.getAllBudgets();
    }
}

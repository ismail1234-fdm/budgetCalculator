package com.budgetCalculator.config;

import com.budgetCalculator.repository.BudgetRepository;
import com.budgetCalculator.services.BudgetServiceImp;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@Setter
public class BudgetConfiguration {
    @Bean
    BudgetServiceImp budgetServiceImp(BudgetRepository budgetRepository) {
        return new BudgetServiceImp(budgetRepository);
    }
}

package com.budgetCalculator.config;

import com.budgetCalculator.repository.ExpenditureRepository;
import com.budgetCalculator.services.ExpenditureServiceImp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExpenditureConfiguration {

    @Bean
    ExpenditureServiceImp expenditureServiceImp(ExpenditureRepository ExpenditureRepository) {
        return new ExpenditureServiceImp(ExpenditureRepository);
    }
}

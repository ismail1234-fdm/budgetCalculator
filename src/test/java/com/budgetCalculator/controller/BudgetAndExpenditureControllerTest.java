package com.budgetCalculator.controller;


import com.budgetCalculator.entity.Budget;
import com.budgetCalculator.entity.Expenditure;
import com.budgetCalculator.services.BudgetServiceImp;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@AutoConfigureMockMvc(addFilters = false) // Security is disabled for testing
public class BudgetAndExpenditureControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BudgetServiceImp budgetServiceImp;

    @Test
    public void greetingShouldReturn() throws Exception {
        this.mockMvc.perform(get("/hello")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Hello World")));
    }

    @Test
    public void findBudgetbyId() throws Exception {
        List<Expenditure> expenditureList = new ArrayList<>();
        Expenditure expenditure = Expenditure.builder().expenditure(1234).id(12).build();
        expenditureList.add(expenditure);
        Budget expectedBudget = Budget.builder().budget(123.5).id(123)
                .expenditureList(expenditureList).build();

        when(budgetServiceImp.findBudgetbyId(2)).thenReturn(expectedBudget);

       this.mockMvc.perform(post("/getBudgetById")
                .secure(false)
                .content(objectMapper.writeValueAsString(2))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("123"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.expenditureList[0].expenditure").value("1234.0"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.budget").value("123.5"));

    }
}

package com.budgetCalculator.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "budget", schema = "targetSchemaName")
public class Budget {

    @Id
    @NotNull
    @Column(name = "budgetId")
    private Integer id;

    @NotNull
    @Column(name = "budget")
    private double budget;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "budget_id")
    private List<Expenditure> expenditureList;

}

package com.budgetCalculator.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "expenditure", schema = "targetSchemaName")
public class Expenditure {

    @Id
    @NotNull
    @Column(name = "expenditureId")
    private Integer id;

    @Nullable
    @Column(name = "expenditure")
    private double expenditure;
}

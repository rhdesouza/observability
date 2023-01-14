package com.example.observability.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class Categoria {

    private Integer id;
    private String categoria;
    private BigDecimal valorDiariaMinima;

}
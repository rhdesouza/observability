package com.example.observability.domain.entities;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Locacao {

    private Integer id;
    private Veiculo veiculo;
    private Cliente cliente;
    private Date dataLocacao;
    private Date dataDevolucao;
    private BigDecimal valor;

}
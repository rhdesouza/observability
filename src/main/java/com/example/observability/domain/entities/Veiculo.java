package com.example.observability.domain.entities;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class Veiculo {

    private Integer id;
    private Categoria categoria;
    private Montadora montadora;
    private String modelo;
    private Integer anoFabricacao;
    private Integer anoModelo;
    private BigDecimal valorFipe;
    @Enumerated(EnumType.STRING)
    private StatusVeiculo status;

}

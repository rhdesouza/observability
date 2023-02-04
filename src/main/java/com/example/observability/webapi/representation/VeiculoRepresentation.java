package com.example.observability.webapi.representation;

import com.example.observability.domain.entities.Veiculo;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@JsonSerialize
public class VeiculoRepresentation implements Serializable {

    private Integer id;
    private String categoria;
    private String montadora;
    private String modelo;
    private Integer anoFabricacao;
    private Integer anoModelo;
    private BigDecimal valorFipe;
    private String status;

    public VeiculoRepresentation(@NotNull Veiculo veiculoDomain) {
        this.id = veiculoDomain.getId();
        this.categoria = veiculoDomain.getCategoria().getCategoria();
        this.montadora = veiculoDomain.getMontadora().getMontadora();
        this.modelo = veiculoDomain.getModelo();
        this.anoFabricacao = veiculoDomain.getAnoFabricacao();
        this.anoModelo = veiculoDomain.getAnoModelo();
        this.valorFipe = veiculoDomain.getValorFipe();
        this.status = veiculoDomain.getStatus().name();
    }
}

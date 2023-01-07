package com.example.observability.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class Locacao {

    private Integer id;
    private Veiculo veiculo;
    private Cliente cliente;
    private LocalDateTime dataLocacao;
    private LocalDateTime dataDevolucao;
    private BigDecimal valor;

    public Locacao(Veiculo veiculo, Cliente cliente) {
        this.veiculo = veiculo;
        this.cliente = cliente;
        this.dataLocacao = LocalDateTime.now();
    }

    public void setDevolucao() {
        this.dataDevolucao = LocalDateTime.now();
        this.veiculo.setStatus(StatusVeiculo.Disponivel);
        this.valor = this.calculateLocation();
    }

    //Todo: Implementar a regra correta sobre o valor da locação.
    @NotNull
    @Contract(value = " -> new", pure = true)
    private BigDecimal calculateLocation() {
        return new BigDecimal("500.00");
    }

}
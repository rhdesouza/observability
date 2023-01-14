package com.example.observability.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.math.MathContext;
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

    @NotNull
    @Contract(value = " -> new", pure = true)
    private BigDecimal calculateLocation() {
        int mesAno = 12;
        int diasMes = 30;
        int diffAnoVeiculo = Math.subtractExact(LocalDateTime.now().getYear(), this.veiculo.getAnoModelo());
        BigDecimal valorAluguelDiario = this.veiculo.getValorFipe()
                .divide(BigDecimal.valueOf(diffAnoVeiculo), MathContext.DECIMAL32)
                .divide(new BigDecimal(mesAno), MathContext.DECIMAL32)
                .divide(new BigDecimal(diasMes), MathContext.DECIMAL32);

        return valorAluguelDiario.compareTo(this.veiculo.getCategoria().getValorDiariaMinima()) == 1 ?  valorAluguelDiario : this.veiculo.getCategoria().getValorDiariaMinima();
    }
}
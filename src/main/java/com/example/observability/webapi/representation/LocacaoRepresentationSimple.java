package com.example.observability.webapi.representation;

import com.example.observability.domain.entities.Cliente;
import com.example.observability.domain.entities.Locacao;
import com.example.observability.domain.entities.Veiculo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class LocacaoRepresentationSimple {

    private Integer id;
    private Veiculo veiculo;
    private Cliente cliente;
    private LocalDateTime dataLocacao;
    private LocalDateTime dataDevolucao;
    private BigDecimal valor;

    public LocacaoRepresentationSimple(Locacao locacaoDomain){
        this.id = locacaoDomain.getId();
        this.veiculo = locacaoDomain.getVeiculo();
        this.cliente = locacaoDomain.getCliente();
        this.dataLocacao = locacaoDomain.getDataLocacao();
        this.dataDevolucao = locacaoDomain.getDataDevolucao();
        this.valor = locacaoDomain.getValor();
    }


}

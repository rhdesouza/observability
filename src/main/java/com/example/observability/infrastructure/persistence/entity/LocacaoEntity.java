package com.example.observability.infrastructure.persistence.entity;

import com.example.observability.domain.entities.Locacao;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity(name = "locacao")
@NoArgsConstructor
@Data
public class LocacaoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "veiculo_id")
    private VeiculoEntity veiculoEntity;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "cliente_id")
    private ClienteEntity clienteEntity;

    @Column(name = "data_locacao")
    private LocalDateTime dataLocacao;

    @Column(name = "data_devolucao")
    private LocalDateTime dataDevolucao;

    @Column(name = "valor")
    private BigDecimal valor;

    public LocacaoEntity(@NotNull Locacao locacaoDomain) {
        this.id = locacaoDomain.getId();
        this.veiculoEntity = new VeiculoEntity(locacaoDomain.getVeiculo());
        this.clienteEntity = new ClienteEntity(locacaoDomain.getCliente());
        this.dataLocacao = locacaoDomain.getDataLocacao();
        this.dataDevolucao = locacaoDomain.getDataDevolucao();
        this.valor = locacaoDomain.getValor();
    }

    public Locacao toDomain() {
        return Locacao.builder()
                .id(this.id)
                .veiculo(this.veiculoEntity.toDomain())
                .cliente(this.clienteEntity.toDomain())
                .dataLocacao(this.dataLocacao)
                .dataDevolucao(this.dataDevolucao)
                .valor(this.valor)
                .build();
    }
}

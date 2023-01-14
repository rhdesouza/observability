package com.example.observability.infrastructure.persistence.entity;

import com.example.observability.domain.entities.Categoria;
import com.example.observability.domain.entities.Montadora;
import com.example.observability.domain.entities.Veiculo;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Entity(name = "veiculo")
public class VeiculoEntity {

    @Id
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "categoria_id")
    private CategoriaEntity categoriaEntity;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "montadora_id")
    private MontadoraEntity montadoraEntity;

    @Column(name = "modelo")
    private String modelo;

    @Column(name = "ano_fabricacao")
    private Integer anoFabricacao;

    @Column(name = "ano_modelo")
    private Integer anoModelo;

    @Column(name = "valor_fipe")
    private BigDecimal valorFipe;

    @Column(name = "status_veiculo")
    @Enumerated(EnumType.STRING)
    private StatusVeiculo status;

    public VeiculoEntity(@NotNull Veiculo veiculoDomain) {
        this.id = veiculoDomain.getId();
        this.categoriaEntity = new CategoriaEntity(veiculoDomain.getCategoria());
        this.montadoraEntity = new MontadoraEntity(veiculoDomain.getMontadora());
        this.modelo = veiculoDomain.getModelo();
        this.anoFabricacao = veiculoDomain.getAnoFabricacao();
        this.anoModelo = veiculoDomain.getAnoModelo();
        this.valorFipe = veiculoDomain.getValorFipe();
        this.status = StatusVeiculo.valueOf(veiculoDomain.getStatus().name());
    }

    public Veiculo toDomain() {
        return Veiculo.builder()
                .id(this.id)
                .categoria(new Categoria(this.categoriaEntity.getId(), this.categoriaEntity.getCategoria(), this.categoriaEntity.getValorDiariaMinima()))
                .montadora(new Montadora(this.montadoraEntity.getId(), this.montadoraEntity.getMontadora()))
                .modelo(this.modelo)
                .anoFabricacao(this.anoFabricacao)
                .anoModelo(this.anoModelo)
                .valorFipe(this.valorFipe)
                .status(com.example.observability.domain.entities.StatusVeiculo.valueOf(this.status.name()))
                .build();
    }

}

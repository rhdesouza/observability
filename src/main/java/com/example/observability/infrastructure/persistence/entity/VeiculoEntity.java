package com.example.observability.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity(name = "veiculo")
public class VeiculoEntity {

    @Id
    private Integer id;

    @OneToOne
    @JoinColumn(name = "categoria_id")
    private CategoriaEntity categoriaEntity;

    @OneToOne
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

}

package com.example.observability.infrastructure.persistence.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity(name = "locacao")
public class LocacaoEntity {

    @Id
    private Integer id;

    @OneToOne
    @JoinColumn(name = "veiculo_id")
    private VeiculoEntity veiculoEntity;

    @OneToOne
    @JoinColumn(name = "cliente_id")
    private ClienteEntity clienteEntity;

    @Column(name = "data_locacao")
    private Date dataLocacao;

    @Column(name = "data_devolucao")
    private Date dataDevolucao;

    @Column(name = "valor")
    private BigDecimal valor;

}

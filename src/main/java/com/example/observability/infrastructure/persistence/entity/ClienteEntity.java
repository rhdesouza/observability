package com.example.observability.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity(name = "cliente")
@Data
public class ClienteEntity {

    @Id
    private Integer id;

    @Column(name="nome")
    private String nome;

    @Column(name="cpf")
    private String cpf;

    @Column(name="endereco")
    private String endereco;

    @Column(name="status_cliente")
    @Enumerated(EnumType.STRING)
    private StatusCliente statusCliente;

}

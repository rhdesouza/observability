package com.example.observability.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Cliente {
    private Integer id;
    private String nome;
    private String cpf;
    private String endereco;
    private StatusCliente statusCliente;
}

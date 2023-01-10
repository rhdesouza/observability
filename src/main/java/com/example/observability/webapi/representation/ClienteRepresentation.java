package com.example.observability.webapi.representation;

import com.example.observability.domain.entities.Cliente;
import com.example.observability.domain.entities.StatusCliente;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ClienteRepresentation {

    private Integer id;
    private String nome;
    private String cpf;
    private String endereco;
    private StatusCliente statusCliente;

    public ClienteRepresentation(Cliente cliente) {
        this.id = cliente.getId();
        this.nome = cliente.getNome();
        this.cpf = cliente.getCpf();
        this.endereco = cliente.getEndereco();
        this.statusCliente = StatusCliente.valueOf(cliente.getStatusCliente().name());
    }

    public Cliente toDomain() {
        StatusCliente statusClienteDomain = this.statusCliente == null ?  StatusCliente.Ativo : this.statusCliente;

        return Cliente.builder()
                .id(this.id)
                .nome(this.nome)
                .cpf(this.cpf)
                .endereco(this.endereco)
                .statusCliente(statusClienteDomain)
                .build();
    }

}

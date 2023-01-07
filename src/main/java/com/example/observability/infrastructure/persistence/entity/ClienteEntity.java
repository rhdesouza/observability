package com.example.observability.infrastructure.persistence.entity;

import com.example.observability.domain.entities.Cliente;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "cliente")
@Data
@NoArgsConstructor
public class ClienteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "cpf")
    private String cpf;

    @Column(name = "endereco")
    private String endereco;

    @Column(name = "status_cliente")
    @Enumerated(EnumType.STRING)
    private StatusCliente statusCliente;

    public ClienteEntity(Cliente cliente){
        this.id = cliente.getId();
        this.nome = cliente.getNome();
        this.cpf = cliente.getCpf();
        this.endereco = cliente.getEndereco();
        this.statusCliente = StatusCliente.valueOf(cliente.getStatusCliente().name());
    }

    public Cliente toDomain() {
        return Cliente.builder()
                .id(this.id)
                .nome(this.nome)
                .cpf(this.cpf)
                .endereco(this.endereco)
                .statusCliente(
                        com.example.observability.domain.entities.StatusCliente.valueOf(this.statusCliente.name())
                )
                .build();
    }


}

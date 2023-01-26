package com.example.observability.webapi.unit.representation;

import com.example.observability.domain.entities.Cliente;
import com.example.observability.domain.entities.StatusCliente;
import com.example.observability.webapi.representation.ClienteRepresentation;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ClienteRepresentationTest {

    @Test
    void constructor() {
        Cliente clienteDomain = new EasyRandom().nextObject(Cliente.class);

        ClienteRepresentation clienteRepresentation = new ClienteRepresentation(clienteDomain);

        Assertions.assertEquals(clienteDomain.getId(), clienteRepresentation.getId());
        Assertions.assertEquals(clienteDomain.getNome(), clienteRepresentation.getNome());
        Assertions.assertEquals(clienteDomain.getCpf(), clienteRepresentation.getCpf());
        Assertions.assertEquals(clienteDomain.getEndereco(), clienteRepresentation.getEndereco());
        Assertions.assertEquals(clienteDomain.getStatusCliente().name(), clienteRepresentation.getStatusCliente().name());
    }

    @Test
    void toDomain(){
        ClienteRepresentation clienteRepresentation = new EasyRandom().nextObject(ClienteRepresentation.class);

        Cliente clienteDomain = clienteRepresentation.toDomain();

        Assertions.assertEquals(clienteDomain.getId(), clienteRepresentation.getId());
        Assertions.assertEquals(clienteDomain.getNome(), clienteRepresentation.getNome());
        Assertions.assertEquals(clienteDomain.getCpf(), clienteRepresentation.getCpf());
        Assertions.assertEquals(clienteDomain.getEndereco(), clienteRepresentation.getEndereco());
        Assertions.assertEquals(clienteDomain.getStatusCliente().name(), clienteRepresentation.getStatusCliente().name());
    }

    @Test
    void toDomain_StatusNull(){
        ClienteRepresentation clienteRepresentation = new EasyRandom().nextObject(ClienteRepresentation.class);
        clienteRepresentation.setStatusCliente(null);

        Cliente clienteDomain = clienteRepresentation.toDomain();

        Assertions.assertEquals(clienteDomain.getId(), clienteRepresentation.getId());
        Assertions.assertEquals(clienteDomain.getNome(), clienteRepresentation.getNome());
        Assertions.assertEquals(clienteDomain.getCpf(), clienteRepresentation.getCpf());
        Assertions.assertEquals(clienteDomain.getEndereco(), clienteRepresentation.getEndereco());
        Assertions.assertEquals(clienteDomain.getStatusCliente().name(), StatusCliente.Ativo.name());
    }
}

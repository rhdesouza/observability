package com.example.observability.domain.unit.entities;

import com.example.observability.domain.entities.Cliente;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CategoriaTest {

    @Test
    void constructCategoriaDomain() {
        Cliente clienteRandom = new EasyRandom().nextObject(Cliente.class);

        Cliente cliente = new Cliente(clienteRandom.getId(),
                clienteRandom.getNome(),
                clienteRandom.getCpf(),
                clienteRandom.getEndereco(),
                clienteRandom.getStatusCliente());

        Assertions.assertEquals(cliente, clienteRandom);
        Assertions.assertEquals(cliente.toString(), clienteRandom.toString());
        Assertions.assertEquals(cliente.hashCode(), clienteRandom.hashCode());
        Assertions.assertTrue(cliente.equals(clienteRandom));
    }
}

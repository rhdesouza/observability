package com.example.observability.domain.unit.entities;

import com.example.observability.domain.entities.StatusCliente;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.engine.TestExecutionResult;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class StatusClienteTest {

    @Test
    void enumStatusClienteTest_Ativo() {
        StatusCliente statusCliente = StatusCliente.Ativo;

        Assertions.assertEquals(statusCliente, StatusCliente.Ativo);
    }

    @Test
    void enumStatusClienteTest_Inativo() {
        StatusCliente statusCliente = StatusCliente.Inativo;

        Assertions.assertEquals(statusCliente, StatusCliente.Inativo);
    }
}

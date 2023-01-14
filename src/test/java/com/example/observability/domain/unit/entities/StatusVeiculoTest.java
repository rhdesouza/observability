package com.example.observability.domain.unit.entities;

import com.example.observability.domain.entities.StatusVeiculo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class StatusVeiculoTest {

    @Test
    void enumStatusVeiculoTest_Disponivel() {
        StatusVeiculo statusVeiculo = StatusVeiculo.Disponivel;

        Assertions.assertEquals(statusVeiculo, StatusVeiculo.Disponivel);
    }

    @Test
    void enumStatusVeiculoTest_Alugado() {
        StatusVeiculo statusVeiculo = StatusVeiculo.Alugado;

        Assertions.assertEquals(statusVeiculo, StatusVeiculo.Alugado);
    }

    @Test
    void enumStatusVeiculoTest_Vendido() {
        StatusVeiculo statusVeiculo = StatusVeiculo.Vendido;

        Assertions.assertEquals(statusVeiculo, StatusVeiculo.Vendido);
    }
}

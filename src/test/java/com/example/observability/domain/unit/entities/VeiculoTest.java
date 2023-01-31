package com.example.observability.domain.unit.entities;

import com.example.observability.domain.entities.Veiculo;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class VeiculoTest {

    @Test
    void constructVeiculoDomain() {
        Veiculo veiculoRandom = new EasyRandom().nextObject(Veiculo.class);

        Veiculo veiculo = Veiculo.builder()
                .id(veiculoRandom.getId())
                .categoria(veiculoRandom.getCategoria())
                .montadora(veiculoRandom.getMontadora())
                .modelo(veiculoRandom.getModelo())
                .anoFabricacao(veiculoRandom.getAnoFabricacao())
                .anoModelo(veiculoRandom.getAnoModelo())
                .valorFipe(veiculoRandom.getValorFipe())
                .status(veiculoRandom.getStatus())
                .build(
                );

        Assertions.assertEquals(veiculo, veiculoRandom);
        Assertions.assertEquals(veiculo.toString(), veiculoRandom.toString());
        Assertions.assertEquals(veiculo.hashCode(), veiculoRandom.hashCode());
        Assertions.assertTrue(veiculo.equals(veiculoRandom));
    }
}

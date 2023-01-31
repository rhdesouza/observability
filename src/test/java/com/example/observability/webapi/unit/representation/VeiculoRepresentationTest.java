package com.example.observability.webapi.unit.representation;

import com.example.observability.domain.entities.Veiculo;
import com.example.observability.webapi.representation.VeiculoRepresentation;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class VeiculoRepresentationTest {

    @Test
    void constructor() {
        Veiculo veiculoDomain = new EasyRandom().nextObject(Veiculo.class);

        VeiculoRepresentation veiculoRepresentation = new VeiculoRepresentation(veiculoDomain);

        Assertions.assertEquals(veiculoDomain.getId(), veiculoRepresentation.getId());
        Assertions.assertEquals(veiculoDomain.getCategoria().getCategoria(), veiculoRepresentation.getCategoria());
        Assertions.assertEquals(veiculoDomain.getMontadora().getMontadora(), veiculoRepresentation.getMontadora());
        Assertions.assertEquals(veiculoDomain.getModelo(), veiculoRepresentation.getModelo());
        Assertions.assertEquals(veiculoDomain.getAnoFabricacao(), veiculoRepresentation.getAnoFabricacao());
        Assertions.assertEquals(veiculoDomain.getAnoModelo(), veiculoRepresentation.getAnoModelo());
        Assertions.assertEquals(veiculoDomain.getValorFipe(), veiculoRepresentation.getValorFipe());
        Assertions.assertEquals(veiculoDomain.getStatus().name(), veiculoRepresentation.getStatus());
    }
}

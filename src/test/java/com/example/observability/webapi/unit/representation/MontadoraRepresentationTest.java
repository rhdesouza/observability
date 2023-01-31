package com.example.observability.webapi.unit.representation;

import com.example.observability.domain.entities.Montadora;
import com.example.observability.webapi.representation.MontadoraRepresentation;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MontadoraRepresentationTest {

    @Test
    void constructor() {
        Montadora montadora = new EasyRandom().nextObject(Montadora.class);

        MontadoraRepresentation montadoraRepresentation = new MontadoraRepresentation(montadora);

        Assertions.assertEquals(montadora.getMontadora(), montadoraRepresentation.getMontadora());
    }
}

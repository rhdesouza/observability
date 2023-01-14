package com.example.observability.domain.unit.entities;

import com.example.observability.domain.entities.Montadora;
import com.example.observability.infrastructure.persistence.repository.CategoriaRepository;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MontadoraTest {

    @Test
    void constructMontadoraDomain() {
        Montadora montadoraRandom = new EasyRandom().nextObject(Montadora.class);

        Montadora montadora = new Montadora(montadoraRandom.getId(), montadoraRandom.getMontadora());

        Assertions.assertEquals(montadora, montadoraRandom);
        Assertions.assertEquals(montadora.toString(), montadoraRandom.toString());
        Assertions.assertEquals(montadora.hashCode(), montadoraRandom.hashCode());
        Assertions.assertTrue(montadora.equals(montadoraRandom));
    }
}

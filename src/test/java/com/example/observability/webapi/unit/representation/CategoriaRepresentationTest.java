package com.example.observability.webapi.unit.representation;

import com.example.observability.domain.entities.Categoria;
import com.example.observability.webapi.representation.CategoriaRepresentation;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CategoriaRepresentationTest {


    @Test
    void constructor() {
        Categoria categoriaDomain = new EasyRandom().nextObject(Categoria.class);

        CategoriaRepresentation categoriaRepresentation = new CategoriaRepresentation(categoriaDomain);

        Assertions.assertEquals(categoriaRepresentation.getCategoria(), categoriaDomain.getCategoria());
        Assertions.assertEquals(categoriaRepresentation.getValorDiariaMinima(), categoriaDomain.getValorDiariaMinima());
    }
}

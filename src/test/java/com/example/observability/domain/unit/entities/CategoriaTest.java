package com.example.observability.domain.unit.entities;

import com.example.observability.domain.entities.Categoria;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CategoriaTest {

    @Test
    void constructCategoriaDomain(){
        Categoria categoriaRandom = new EasyRandom().nextObject(Categoria.class);

        Categoria categoria = new Categoria(categoriaRandom.getId(),categoriaRandom.getCategoria(), categoriaRandom.getValorDiariaMinima());

        Assertions.assertEquals(categoria, categoriaRandom);
    }
}

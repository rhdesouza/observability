package com.example.observability.application.unit.service;

import com.example.observability.application.service.CategoriaServiceImp;
import com.example.observability.domain.entities.Categoria;
import com.example.observability.infrastructure.persistence.entity.CategoriaEntity;
import com.example.observability.infrastructure.persistence.repository.CategoriaRepository;
import com.example.observability.webapi.exception.NotFoundException;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
public class CategoriaServiceImpTest {

    @Autowired
    CategoriaServiceImp categoriaServiceImp;

    @MockBean
    CategoriaRepository categoriaRepository;

    @Test
    void cfindByIdCategoria_returnOk() {
        CategoriaEntity categoriaEntityMock = new EasyRandom().nextObject(CategoriaEntity.class);
        Mockito.when(categoriaRepository.findById(any(Long.class))).thenReturn(Optional.of(categoriaEntityMock));

        Categoria categoriaReturn = categoriaServiceImp.findByIdCategoria(any(Long.class));

        Assertions.assertEquals(categoriaReturn, categoriaEntityMock.toDomain());
    }

    @Test
    void findByIdCategoria_returnNotFoundException() {
        Mockito.when(categoriaRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        NotFoundException exception = Assertions.assertThrows(NotFoundException.class,
                () -> categoriaServiceImp.findByIdCategoria(any(Long.class)));

        Assertions.assertEquals("Method: findByIdCategoria | retunr not found", exception.getMessage());
    }

    @Test
    void findAllCategoria_returnListCategories() {
        List<CategoriaEntity> listCategoriaEntityMock = new EasyRandom()
                .objects(CategoriaEntity.class,5)
                .collect(Collectors.toList());
        Mockito.when(categoriaRepository.findAll()).thenReturn(listCategoriaEntityMock);

        List<Categoria> expectReturn = listCategoriaEntityMock.stream()
                .map(CategoriaEntity::toDomain).toList();

        List<Categoria> categoriaReturn = categoriaServiceImp.findAllCategoria();

        Assertions.assertEquals(categoriaReturn.size(), 5);
        Assertions.assertEquals(categoriaReturn, expectReturn);
    }



}

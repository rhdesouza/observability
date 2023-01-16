package com.example.observability.application.unit.service;

import com.example.observability.application.service.MontadoraServiceImp;
import com.example.observability.domain.entities.Montadora;
import com.example.observability.infrastructure.persistence.entity.MontadoraEntity;
import com.example.observability.infrastructure.persistence.repository.MontadoraRepository;
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
public class MontadoraServiceImpTest {

    @Autowired
    MontadoraServiceImp montadoraServiceImp;

    @MockBean
    MontadoraRepository montadoraRepository;

    @Test
    void findByIdMontadora_Success() {
        MontadoraEntity montadoraEntityMock = new EasyRandom().nextObject(MontadoraEntity.class);
        Mockito.when(montadoraRepository.findById(any(Long.class))).thenReturn(Optional.of(montadoraEntityMock));

        Optional<Montadora> montadoraReturn = montadoraServiceImp.findByIdMontadora(any(Long.class));

        Assertions.assertEquals(montadoraReturn.get(), montadoraEntityMock.toDomain());
    }

    @Test
    void findByIdMontadora_NotFoundException() {
        Mockito.when(montadoraRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        NotFoundException exception = Assertions.assertThrows(NotFoundException.class,
                () -> montadoraServiceImp.findByIdMontadora(any(Long.class)));

        Assertions.assertEquals("Method: findByIdMontadora | retunr not found", exception.getMessage());

    }

    @Test
    void getAllMontadora_Success() {
        List<MontadoraEntity> montadoraEntitiesMock = new EasyRandom()
                .objects(MontadoraEntity.class, 15)
                .collect(Collectors.toList());
        List<Montadora> montadoraExpectReturn = montadoraEntitiesMock.stream().map(MontadoraEntity::toDomain).toList();

        Mockito.when(montadoraRepository.findAll()).thenReturn(montadoraEntitiesMock);

        List<Montadora> montadorasReturns = montadoraServiceImp.getAllMontadora();

        Assertions.assertEquals(15, montadorasReturns.size());
        Assertions.assertEquals(montadoraExpectReturn, montadorasReturns);
    }

}

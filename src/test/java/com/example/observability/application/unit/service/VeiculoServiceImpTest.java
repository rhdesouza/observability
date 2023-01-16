package com.example.observability.application.unit.service;

import com.example.observability.application.service.VeiculoServiceImp;
import com.example.observability.domain.entities.StatusVeiculo;
import com.example.observability.domain.entities.Veiculo;
import com.example.observability.infrastructure.persistence.entity.VeiculoEntity;
import com.example.observability.infrastructure.persistence.repository.VeiculoRepository;
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
public class VeiculoServiceImpTest {

    @Autowired
    VeiculoServiceImp veiculoServiceImp;

    @MockBean
    VeiculoRepository veiculoRepository;


    @Test
    void findByIdVeiculo_Success() {
        VeiculoEntity veiculoEntityMock = new EasyRandom().nextObject(VeiculoEntity.class);
        Mockito.when(veiculoRepository.findById(any(Long.class))).thenReturn(Optional.of(veiculoEntityMock));

        Optional<Veiculo> veiculoReturn = veiculoServiceImp.findByIdVeiculo(any(Long.class));

        Assertions.assertEquals(veiculoReturn.get(), veiculoEntityMock.toDomain());
    }

    @Test
    void findByIdVeiculo_notFoundException() {
        Long idVeiculo = 1l;
        Mockito.when(veiculoRepository.findById(idVeiculo)).thenReturn(Optional.empty());

        NotFoundException exception = Assertions.assertThrows(NotFoundException.class,
                () -> veiculoServiceImp.findByIdVeiculo(idVeiculo));

        Assertions.assertEquals("Method: findByIdVeiculo | retunr not found for id: " + idVeiculo, exception.getMessage());
    }

    @Test
    void getAllVeiculo_success() {
        List<VeiculoEntity> veiculoEntitiesMock = new EasyRandom()
                .objects(VeiculoEntity.class, 10)
                .collect(Collectors.toList());
        List<Veiculo> veiculosExpectReturn = veiculoEntitiesMock.stream().map(VeiculoEntity::toDomain).toList();

        Mockito.when(veiculoRepository.findAll()).thenReturn(veiculoEntitiesMock);

        List<Veiculo> veiculosReturn = veiculoServiceImp.getAllVeiculo();

        Assertions.assertEquals(10, veiculosReturn.size());
        Assertions.assertEquals(veiculosReturn, veiculosExpectReturn);
    }

    @Test
    void setStatusVeiculo_success() {
        Veiculo veiculoMock = new EasyRandom().nextObject(Veiculo.class);
        veiculoMock.setStatus(StatusVeiculo.Alugado);

        VeiculoEntity veiculoSavedMock = new EasyRandom().nextObject(VeiculoEntity.class);
        veiculoSavedMock.setStatus(
                com.example.observability.infrastructure.persistence.entity.StatusVeiculo.Disponivel
        );

        Mockito.when(veiculoRepository.save(any(VeiculoEntity.class))).thenReturn(veiculoSavedMock);

        Veiculo veiculoReturns = veiculoServiceImp.setStatusVeiculo(veiculoMock, StatusVeiculo.Disponivel);

        Assertions.assertTrue(veiculoReturns.getStatus().equals(StatusVeiculo.Disponivel));

    }

}

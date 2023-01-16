package com.example.observability.application.unit.service;

import com.example.observability.application.service.LocacaoServiceImp;
import com.example.observability.domain.entities.Cliente;
import com.example.observability.domain.entities.Locacao;
import com.example.observability.domain.entities.StatusVeiculo;
import com.example.observability.domain.entities.Veiculo;
import com.example.observability.domain.interfaces.ClienteService;
import com.example.observability.domain.interfaces.VeiculoService;
import com.example.observability.infrastructure.persistence.entity.LocacaoEntity;
import com.example.observability.infrastructure.persistence.repository.ClienteRepository;
import com.example.observability.infrastructure.persistence.repository.LocacaoRepository;
import com.example.observability.webapi.exception.BusinessException;
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
public class LocacaoServiceImpTest {

    @Autowired
    LocacaoServiceImp locacaoServiceImp;

    @MockBean
    LocacaoRepository locacaoRepository;

    @MockBean
    ClienteService clienteService;

    @MockBean
    private ClienteRepository clienteRepository;

    @MockBean
    private VeiculoService veiculoService;


    @Test
    void findByIdLocacao_success() {
        LocacaoEntity locacaoEntityMock = new EasyRandom().nextObject(LocacaoEntity.class);
        Mockito.when(locacaoRepository.findById(any(Long.class))).thenReturn(Optional.of(locacaoEntityMock));

        Locacao locacaoReturn = locacaoServiceImp.findByIdLocacao(any(Long.class));

        Assertions.assertEquals(locacaoReturn, locacaoEntityMock.toDomain());
    }

    @Test
    void findByIdLocacao_notFoundException() {
        Long idLocacao = 1l;
        Mockito.when(locacaoRepository.findById(idLocacao)).thenReturn(Optional.empty());

        NotFoundException exception = Assertions.assertThrows(NotFoundException.class,
                () -> locacaoServiceImp.findByIdLocacao(idLocacao));

        Assertions.assertEquals("Method: findByIdLocacao | Cliente not found", exception.getMessage());
    }

    @Test
    void getAllLocacao_success() {
        List<LocacaoEntity> locacaoEntitiesMock = new EasyRandom()
                .objects(LocacaoEntity.class, 10)
                .collect(Collectors.toList());
        List<Locacao> locacaoExpectReturn = locacaoEntitiesMock.stream().map(LocacaoEntity::toDomain).toList();

        Mockito.when(locacaoRepository.findAll()).thenReturn(locacaoEntitiesMock);

        List<Locacao> veiculosReturn = locacaoServiceImp.getAllLocacao();

        Assertions.assertEquals(10, veiculosReturn.size());
        Assertions.assertEquals(veiculosReturn, locacaoExpectReturn);
    }

    @Test
    void setLocacao_success() {
        Cliente clienteMock = new EasyRandom().nextObject(Cliente.class);
        Mockito.when(clienteService.findByIdCliente(any(Long.class))).thenReturn(Optional.of(clienteMock));

        Veiculo veiculoMock = new EasyRandom().nextObject(Veiculo.class);
        veiculoMock.setStatus(StatusVeiculo.Disponivel);
        Mockito.when(veiculoService.findByIdVeiculo(any(Long.class))).thenReturn(Optional.of(veiculoMock));

        LocacaoEntity locacaoEntityMock = new LocacaoEntity(new Locacao(veiculoMock, clienteMock));
        Locacao locacaoExpect = locacaoEntityMock.toDomain();
        Mockito.when(locacaoRepository.save(any(LocacaoEntity.class))).thenReturn(locacaoEntityMock);

        Locacao locacaoReturn = locacaoServiceImp.setLocacao(1l, 1l);

        Assertions.assertNotNull(locacaoReturn);
        Assertions.assertEquals(locacaoReturn, locacaoExpect);
    }

    @Test
    void setLocacao_businessException() {
        Cliente clienteMock = new EasyRandom().nextObject(Cliente.class);
        Mockito.when(clienteService.findByIdCliente(any(Long.class))).thenReturn(Optional.of(clienteMock));

        Veiculo veiculoMock = new EasyRandom().nextObject(Veiculo.class);
        veiculoMock.setStatus(StatusVeiculo.Alugado);
        Mockito.when(veiculoService.findByIdVeiculo(any(Long.class))).thenReturn(Optional.of(veiculoMock));


        BusinessException exception = Assertions.assertThrows(BusinessException.class,
                () -> locacaoServiceImp.setLocacao(1l, 1l));

        Assertions.assertEquals("Vehicle has already been rented, wait until it is returned", exception.getMessage());
    }

    @Test
    void setLocacao_exception() {
        Cliente clienteMock = new EasyRandom().nextObject(Cliente.class);
        Mockito.when(clienteService.findByIdCliente(any(Long.class))).thenReturn(Optional.of(clienteMock));

        Veiculo veiculoMock = new EasyRandom().nextObject(Veiculo.class);
        veiculoMock.setStatus(StatusVeiculo.Disponivel);
        Mockito.when(veiculoService.findByIdVeiculo(any(Long.class))).thenReturn(Optional.of(veiculoMock));

        Mockito.when(locacaoRepository.save(any(LocacaoEntity.class))).thenThrow(new NullPointerException("Error occurred"));

        Exception exception = Assertions.assertThrows(Exception.class,
                () -> locacaoServiceImp.setLocacao(1l, 1l));

        Assertions.assertEquals("Error occurred", exception.getMessage());
    }

    @Test
    void setDevolucao_success() {
        LocacaoEntity locacaoEntityMock = new EasyRandom().nextObject(LocacaoEntity.class);
        Mockito.when(locacaoRepository.findById(any(Long.class))).thenReturn(Optional.of(locacaoEntityMock));
        Mockito.when(veiculoService.setStatusVeiculo(any(Veiculo.class), any(StatusVeiculo.class))).thenReturn(null);
        Mockito.when(locacaoRepository.save(any(LocacaoEntity.class))).thenReturn(locacaoEntityMock);

        Locacao locacaoReturn = locacaoServiceImp.setDevolucao(any(Long.class));

        Assertions.assertNotNull(locacaoReturn);
        Assertions.assertEquals(locacaoReturn, locacaoEntityMock.toDomain());
    }

    @Test
    void setDevolucao_exception () {
        LocacaoEntity locacaoEntityMock = new EasyRandom().nextObject(LocacaoEntity.class);
        Mockito.when(locacaoRepository.findById(any(Long.class))).thenReturn(Optional.of(locacaoEntityMock));
        Mockito.when(veiculoService.setStatusVeiculo(any(Veiculo.class), any(StatusVeiculo.class))).thenThrow(new NullPointerException("Error occurred"));

        Exception exception = Assertions.assertThrows(Exception.class,
                () -> locacaoServiceImp.setDevolucao(any(Long.class)));

        Assertions.assertEquals("Error occurred", exception.getMessage());
    }

}

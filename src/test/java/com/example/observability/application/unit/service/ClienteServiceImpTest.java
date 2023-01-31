package com.example.observability.application.unit.service;

import com.example.observability.application.service.ClienteServiceImp;
import com.example.observability.domain.entities.Cliente;
import com.example.observability.infrastructure.persistence.entity.ClienteEntity;
import com.example.observability.infrastructure.persistence.entity.StatusCliente;
import com.example.observability.infrastructure.persistence.repository.ClienteRepository;
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
class ClienteServiceImpTest {

    @Autowired
    ClienteServiceImp clienteServiceImp;

    @MockBean
    ClienteRepository clienteRepository;

    @Test
    void findByIdCliente_returnOK() {
        ClienteEntity clienteEntityMock = new EasyRandom().nextObject(ClienteEntity.class);
        Mockito.when(clienteRepository.findById(any(Long.class))).thenReturn(Optional.of(clienteEntityMock));

        Cliente clienteReturn = clienteServiceImp.findByIdCliente(any(Long.class));

        Assertions.assertEquals(clienteReturn, clienteEntityMock.toDomain());
    }

    @Test
    void findByIdCliente_returnNotFoundException() {
        Long idCliente = 1l;
        Mockito.when(clienteRepository.findById(idCliente)).thenReturn(Optional.empty());

        NotFoundException exception = Assertions.assertThrows(NotFoundException.class,
                () -> clienteServiceImp.findByIdCliente(idCliente));

        Assertions.assertEquals("Method: findByIdCliente | retunr not found client id: " + idCliente, exception.getMessage());
    }

    @Test
    void getAllClientes_returnListClientes() {
        List<ClienteEntity> clienteEntitiesMock = new EasyRandom()
                .objects(ClienteEntity.class, 10)
                .collect(Collectors.toList());
        List<Cliente> clientesExpectReturn = clienteEntitiesMock.stream().map(ClienteEntity::toDomain).toList();

        Mockito.when(clienteRepository.findAll()).thenReturn(clienteEntitiesMock);

        List<Cliente> clientesReturn = clienteServiceImp.getAllClientes();

        Assertions.assertEquals(10, clientesReturn.size());
        Assertions.assertEquals(clientesReturn, clientesExpectReturn);
    }

    @Test
    void disableCliente_sucess() {
        ClienteEntity clienteMock = new EasyRandom().nextObject(ClienteEntity.class);
        clienteMock.setStatusCliente(StatusCliente.Ativo);
        Mockito.when(clienteRepository.findById(any(Long.class))).thenReturn(Optional.of(clienteMock));
        Mockito.when(clienteRepository.save(any(ClienteEntity.class))).thenReturn(clienteMock);

        Cliente clienteReturn = clienteServiceImp.disableCliente(any(Long.class));

        Assertions.assertEquals(clienteMock.toDomain(), clienteReturn);
    }

    @Test
    void disableCliente_notFoundException() {
        Long idCliente = 15L;
        Mockito.when(clienteRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        NotFoundException exception = Assertions.assertThrows(NotFoundException.class,
                () -> clienteServiceImp.disableCliente(idCliente));

        Assertions.assertEquals("Method: findByIdCliente | retunr not found client id: " + idCliente, exception.getMessage());
    }

    @Test
    void disableCliente_businessException() {
        Long idCliente = 15L;
        ClienteEntity clienteMock = new EasyRandom().nextObject(ClienteEntity.class);
        clienteMock.setStatusCliente(StatusCliente.Inativo);
        Mockito.when(clienteRepository.findById(any(Long.class))).thenReturn(Optional.of(clienteMock));
        Mockito.when(clienteRepository.save(any(ClienteEntity.class))).thenReturn(clienteMock);

        BusinessException exception = Assertions.assertThrows(BusinessException.class,
                () -> clienteServiceImp.disableCliente(idCliente));

        Assertions.assertEquals("Method: disableCliente | client already disabled", exception.getMessage());
    }

    @Test
    void save_successReturn() {
        Cliente clienteMock = new EasyRandom().nextObject(Cliente.class);
        clienteMock.setStatusCliente(com.example.observability.domain.entities.StatusCliente.Ativo);

        ClienteEntity clienteEntityMock = new ClienteEntity(clienteMock);
        Mockito.when(clienteRepository.save(new ClienteEntity(clienteMock))).thenReturn(clienteEntityMock);

        Cliente clienteReturn = clienteServiceImp.save(clienteMock);

        Assertions.assertEquals(clienteMock, clienteReturn);
    }

    @Test
    void save_BusinessException() {
        Cliente clienteMock = new EasyRandom().nextObject(Cliente.class);
        clienteMock.setStatusCliente(com.example.observability.domain.entities.StatusCliente.Inativo);
        clienteMock.setId(null);

        BusinessException exception = Assertions.assertThrows(BusinessException.class,
                () -> clienteServiceImp.save(clienteMock));

        Assertions.assertEquals("Cliente não pode ser cadastrado com o status Inativo", exception.getMessage());
    }

    @Test
    void save_Exception() {
        Cliente clienteMock = new EasyRandom().nextObject(Cliente.class);
        clienteMock.setStatusCliente(com.example.observability.domain.entities.StatusCliente.Ativo);

        Mockito.when(clienteRepository.save(new ClienteEntity(clienteMock))).thenThrow(new NullPointerException("Error occurred"));

        Exception exception = Assertions.assertThrows(Exception.class,
                () -> clienteServiceImp.save(clienteMock));

        Assertions.assertEquals("Error occurred", exception.getMessage());
    }

}

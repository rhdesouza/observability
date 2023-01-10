package com.example.observability.application.service;

import com.example.observability.domain.entities.Cliente;
import com.example.observability.domain.entities.StatusCliente;
import com.example.observability.domain.interfaces.ClienteService;
import com.example.observability.infrastructure.persistence.entity.ClienteEntity;
import com.example.observability.infrastructure.persistence.repository.ClienteRepository;
import com.example.observability.webapi.exception.BusinessException;
import com.example.observability.webapi.exception.NotFoundException;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteServiceImp implements ClienteService {

    Logger logger = LoggerFactory.getLogger(ClienteServiceImp.class);

    @Autowired
    ClienteRepository clienteRepository;

    @Override
    public Optional<Cliente> findByIdCliente(Long idCliente) {
        Optional<ClienteEntity> clienteEntity = clienteRepository.findById(idCliente);
        if (clienteEntity.isPresent()) {
            logger.info("Method: findByIdCliente | retunr {}", clienteEntity.get());
            return Optional.of(clienteEntity.get().toDomain());
        } else {
            logger.info("Method: findByIdCliente | retunr not found");
            throw new NotFoundException("Method: findByIdCliente | retunr not found");
        }
    }

    @Override
    public List<Cliente> getAllClientes() {
        List<ClienteEntity> clienteEntities = clienteRepository.findAll();

        List<Cliente> clientes = clienteEntities.stream().map(ClienteEntity::toDomain).toList();

        logger.info("Method: getAllClientes | find to {} clientes", clientes.size());
        return clientes;
    }

    @Override
    public Cliente disableCliente(Long idCliente) {
        Optional<Cliente> cliente = findByIdCliente(idCliente);
        if (cliente.isPresent()) {
            logger.info("Method: disableCliente | disable cliente {}", cliente.get().getNome());
            return desativaCliente(cliente.get());
        }
        throw new NotFoundException("Method: disableCliente | Cliente not found");
    }

    private Cliente desativaCliente(@NotNull Cliente cliente) {
        if (cliente.getStatusCliente() == StatusCliente.Inativo) {
            throw new NotFoundException("Method: disableCliente | client already disabled");
        } else {
            cliente.setStatusCliente(StatusCliente.Inativo);
            return clienteRepository.save(new ClienteEntity(cliente)).toDomain();
        }
    }
    @Override
    public Cliente save(@NotNull Cliente cliente){
        if (cliente.getStatusCliente() == StatusCliente.Inativo && cliente.getId() == null){
            logger.info("Method: save | {} cannot be saved, inactive status", cliente.getNome());
            throw new BusinessException("Cliente não pode ser cadastrado com o status Inativo");
        }

        try{
            ClienteEntity clienteEntity = clienteRepository.save(new ClienteEntity(cliente));
            logger.info("Method: save | {} saved successfully", cliente.getNome());
            return clienteEntity.toDomain();
        }catch (Exception ex){
            logger.error(String.format("Method: save | %s error saved ", cliente.getNome()));
            throw ex;
        }
    }
}

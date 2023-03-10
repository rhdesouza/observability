package com.example.observability.domain.interfaces;

import com.example.observability.domain.entities.Cliente;

import java.util.List;

public interface ClienteService {

    Cliente findByIdCliente(Long idCliente);

    List<Cliente> getAllClientes();

    Cliente disableCliente(Long idCliente);

    Cliente save(Cliente cliente);
}

package com.example.observability.domain.interfaces;

import com.example.observability.domain.entities.Cliente;

import java.util.List;
import java.util.Optional;

public interface ClienteService {

    Optional<Cliente> findByIdCliente(Long idCliente);

    List<Cliente> getAllClientes();

    Optional<Cliente> disableCliente(Long idCliente);

}

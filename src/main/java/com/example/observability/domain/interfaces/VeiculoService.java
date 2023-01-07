package com.example.observability.domain.interfaces;

import com.example.observability.domain.entities.StatusVeiculo;
import com.example.observability.domain.entities.Veiculo;

import java.util.List;
import java.util.Optional;

public interface VeiculoService {

    Optional<Veiculo> findByIdVeiculo(Long idVeiculo);

    List<Veiculo> getAllVeiculo();

    void setStatusVeiculo(Veiculo veiculo, StatusVeiculo statusVeiculo);

}

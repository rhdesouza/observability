package com.example.observability.application.service;

import com.example.observability.domain.entities.StatusVeiculo;
import com.example.observability.domain.entities.Veiculo;
import com.example.observability.domain.interfaces.VeiculoService;
import com.example.observability.infrastructure.persistence.entity.VeiculoEntity;
import com.example.observability.infrastructure.persistence.repository.VeiculoRepository;
import com.example.observability.webapi.exception.NotFoundException;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VeiculoServiceImp implements VeiculoService {

    Logger logger = LoggerFactory.getLogger(VeiculoServiceImp.class);

    @Autowired
    VeiculoRepository veiculoRepository;

    @Override
    public Optional<Veiculo> findByIdVeiculo(Long idVeiculo) {
        Optional<VeiculoEntity> veiculoEntity = veiculoRepository.findById(idVeiculo);
        if (veiculoEntity.isPresent()) {
            logger.info(String.format("Method: findByIdVeiculo | retunr %s", veiculoEntity.get()));
            return Optional.of(veiculoEntity.get().toDomain());
        } else {
            logger.info("Method: findByIdVeiculo | retunr not found");
            throw new NotFoundException("Method: findByIdVeiculo | retunr not found");
        }
    }

    @Override
    public List<Veiculo> getAllVeiculo() {
        List<VeiculoEntity> veiculoEntities = veiculoRepository.findAll();
        List<Veiculo> veiculos = veiculoEntities.stream()
                .map(montadoraEntity -> montadoraEntity.toDomain()
                ).toList();
        logger.info(String.format("Method: getAllVeiculo | find to %s veiculos", veiculos.size()));
        return veiculos;
    }

    @Override
    public void setStatusVeiculo(@NotNull Veiculo veiculo, StatusVeiculo statusVeiculo) {
        veiculo.setStatus(statusVeiculo);
        veiculoRepository.save(new VeiculoEntity(veiculo));
    }
}

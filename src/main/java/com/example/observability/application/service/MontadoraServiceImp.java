package com.example.observability.application.service;

import com.example.observability.domain.entities.Montadora;
import com.example.observability.domain.interfaces.MontadoraService;
import com.example.observability.infrastructure.persistence.entity.MontadoraEntity;
import com.example.observability.infrastructure.persistence.repository.MontadoraRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MontadoraServiceImp implements MontadoraService {

    Logger logger = LoggerFactory.getLogger(MontadoraServiceImp.class);
    @Autowired
    MontadoraRepository montadoraRepository;

    @Override
    public Optional<Montadora> findByIdMontadora(Long idMontadora) {
        Optional<MontadoraEntity> montadoraEntity = montadoraRepository.findById(idMontadora);
        if (montadoraEntity.isPresent()) {
            logger.info(String.format("Method: findByIdMontadora | retunr %s", montadoraEntity.get()));
            return Optional.of(montadoraEntity.get().toDomain());
        } else {
            logger.info("Method: findByIdMontadora | retunr not found");
            return null;
        }
    }

    @Override
    public List<Montadora> getAllMontadora() {
        List<MontadoraEntity> montadoraEntities = montadoraRepository.findAll();
        List<Montadora> montadoras = montadoraEntities.stream()
                .map(montadoraEntity -> montadoraEntity.toDomain()
                ).toList();
        logger.info(String.format("Method: getAllMontadora | find to %s montadoras", montadoras.size()));
        return montadoras;
    }
}

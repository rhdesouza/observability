package com.example.observability.domain.interfaces;

import com.example.observability.domain.entities.Montadora;

import java.util.List;
import java.util.Optional;

public interface MontadoraService {
    Optional<Montadora> findByIdMontadora(Long idMontadora);

    List<Montadora> getAllMontadora();
}

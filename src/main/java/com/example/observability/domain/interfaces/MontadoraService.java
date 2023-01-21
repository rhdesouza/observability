package com.example.observability.domain.interfaces;

import com.example.observability.domain.entities.Montadora;

import java.util.List;

public interface MontadoraService {
    Montadora findByIdMontadora(Long idMontadora);

    List<Montadora> getAllMontadora();
}

package com.example.observability.domain.interfaces;

import com.example.observability.domain.entities.Categoria;

import java.util.List;
import java.util.Optional;

public interface CategoriaService {
    Categoria findByIdCategoria(Long idCategoria);

    List<Categoria> findAllCategoria();
}

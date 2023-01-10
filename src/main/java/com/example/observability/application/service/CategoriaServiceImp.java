package com.example.observability.application.service;

import com.example.observability.domain.entities.Categoria;
import com.example.observability.domain.interfaces.CategoriaService;
import com.example.observability.infrastructure.persistence.entity.CategoriaEntity;
import com.example.observability.infrastructure.persistence.repository.CategoriaRepository;
import com.example.observability.webapi.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaServiceImp implements CategoriaService {

    Logger logger = LoggerFactory.getLogger(CategoriaServiceImp.class);
    @Autowired
    CategoriaRepository categoriaRepository;

    @Override
    public Categoria findByIdCategoria(Long idCategoria) {
        Optional<CategoriaEntity> categoriaEntity = categoriaRepository.findById(idCategoria);
        if (categoriaEntity.isPresent()) {
            logger.info("Method: findByIdCategoria | retunr {}", categoriaEntity.get());
            return categoriaEntity.get().toDomain();
        }
        throw new NotFoundException("Method: findByIdCategoria | retunr not found");
    }

    @Override
    public List<Categoria> findAllCategoria() {
        List<CategoriaEntity> categoriasEntities = categoriaRepository.findAll();
        List<Categoria> categorias = categoriasEntities.stream()
                .map(CategoriaEntity::toDomain).toList();
        logger.info("Method: findAllCategoria | find to {} categories", categorias.size());
        return categorias;
    }
}

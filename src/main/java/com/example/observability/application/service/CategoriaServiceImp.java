package com.example.observability.application.service;

import com.example.observability.domain.entities.Categoria;
import com.example.observability.domain.interfaces.CategoriaService;
import com.example.observability.infrastructure.persistence.entity.CategoriaEntity;
import com.example.observability.infrastructure.persistence.repository.CategoriaRepository;
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
    public Optional<Categoria> findByIdCategoria(Long idCategoria) {
        Optional<CategoriaEntity> categoriaEntity = categoriaRepository.findById(idCategoria);
        if (categoriaEntity.isPresent()) {
            logger.info(String.format("Method: findByIdCategoria | retunr %s", categoriaEntity.get()));
            return Optional.of(categoriaEntity.get().toDomain());
        } else {
            logger.info("Method: findByIdCategoria | retunr not found");
            return null;
        }
    }

    @Override
    public List<Categoria> findAllCategoria() {
        List<CategoriaEntity> categoriasEntities = categoriaRepository.findAll();
        List<Categoria> categorias = categoriasEntities.stream()
                .map(categoriaEntity -> categoriaEntity.toDomain()
                ).toList();
        logger.info(String.format("Method: findAllCategoria | find to %s categories", categorias.size()));
        return categorias;
    }
}

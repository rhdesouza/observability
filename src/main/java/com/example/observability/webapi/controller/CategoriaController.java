package com.example.observability.webapi.controller;

import com.example.observability.application.service.CategoriaServiceImp;
import com.example.observability.domain.entities.Categoria;
import com.example.observability.webapi.representation.CategoriaRepresentation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/categoria")
public class CategoriaController {

    Logger logger = LoggerFactory.getLogger(CategoriaController.class);

    @Autowired
    private CategoriaServiceImp categoriaServiceImp;


    @Operation(summary = "Retorna uma categoria.", description = "Retorna uma categoria por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna a categoria pesquisada.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CategoriaRepresentation.class))}),
            @ApiResponse(responseCode = "401", description = "Não autorizado;", content = @Content),
            @ApiResponse(responseCode = "404", description = "Categoria não autorizada.", content = @Content)
    })
    @GetMapping(value = "/{idCategoria}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CategoriaRepresentation> getCategoria(
            @Parameter(description = "Id do da categoria")
            @PathVariable(name = "idCategoria") Long idCategoria
    ) {
        logger.info("CategoriaController::getCategoria");
        Categoria categoria = categoriaServiceImp.findByIdCategoria(idCategoria);
        return ResponseEntity.ok(new CategoriaRepresentation(categoria));
    }

    @Operation(summary = "Retorna uma lista de categorias.", description = "Retorna uma lista de categorias")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna a lista de categorias.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CategoriaRepresentation.class))}),
            @ApiResponse(responseCode = "401", description = "Não autorizado;", content = @Content),
            @ApiResponse(responseCode = "404", description = "Categoria não autorizada.", content = @Content)
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CategoriaRepresentation>> getAllCategorias() {
        logger.info("CategoriaController::getAllCategorias");
        List<Categoria> categorias = categoriaServiceImp.findAllCategoria();

        if (categorias.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(
                categorias.stream().map(CategoriaRepresentation::new).toList()
        );
    }
}

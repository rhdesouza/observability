package com.example.observability.webapi.controller;

import com.example.observability.application.service.CategoriaServiceImp;
import com.example.observability.application.service.MontadoraServiceImp;
import com.example.observability.domain.entities.Categoria;
import com.example.observability.domain.entities.Montadora;
import com.example.observability.webapi.representation.CategoriaRepresentation;
import com.example.observability.webapi.representation.MontadoraRepresentation;
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
import java.util.Optional;

@RestController
@RequestMapping("/api/montadora")
public class MontadoraController {

    Logger logger = LoggerFactory.getLogger(MontadoraController.class);

    @Autowired
    private MontadoraServiceImp montadoraServiceImp;


    @Operation(summary = "Retorna uma montadora.",description = "Retorna uma montadora por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna a montadora pesquisada.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = MontadoraRepresentation.class))}),
            @ApiResponse(responseCode = "401", description = "Não autorizado;", content = @Content),
            @ApiResponse(responseCode = "404", description = "Montadora não localizada.", content = @Content)
    })
    @GetMapping(value = "/{idMontadora}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MontadoraRepresentation> getMontadora(
            @Parameter(description = "Id da montadora")
            @PathVariable(name = "idMontadora") Long idMontadora
    ) {
        logger.info("MontadoraController::getMontadora");
        return Optional.ofNullable(montadoraServiceImp.findByIdMontadora(idMontadora))
                .map(result -> new ResponseEntity<MontadoraRepresentation>(
                        new MontadoraRepresentation(result.get()), HttpStatus.OK)
                )
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Retorna uma lista de montadoras.",description = "Retorna uma lista de montadoras")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna a lista de montadoras.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = MontadoraRepresentation.class))}),
            @ApiResponse(responseCode = "401", description = "Não autorizado;", content = @Content),
            @ApiResponse(responseCode = "404", description = "Lista de montadoras não localizada", content = @Content)
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MontadoraRepresentation>> getAllMontadora() {
        logger.info("MontadoraController::getAllMontadora");
        List<Montadora> montadoras = montadoraServiceImp.getAllMontadora();

        if (montadoras.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(
                montadoras.stream().map(montadora -> new MontadoraRepresentation(montadora)).toList()
        );
    }
}

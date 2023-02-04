package com.example.observability.webapi.controller;

import com.example.observability.domain.entities.Veiculo;
import com.example.observability.domain.interfaces.VeiculoService;
import com.example.observability.webapi.representation.VeiculoRepresentation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/veiculo")
public class VeiculoController {

    Logger logger = LoggerFactory.getLogger(VeiculoController.class);

    @Autowired
    private VeiculoService veiculoService;

    @Operation(summary = "Retorna um veiculo.", description = "Retorna um veiculo por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna o veiculo pesquisado.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = VeiculoRepresentation.class))}),
            @ApiResponse(responseCode = "401", description = "Não autorizado;", content = @Content),
            @ApiResponse(responseCode = "404", description = "Veiculo não localizado.", content = @Content)
    })
    @GetMapping(value = "/{idVeiculo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VeiculoRepresentation> getVeiculo(
            @Parameter(description = "Id do veiculo")
            @PathVariable(name = "idVeiculo") Long idVeiculo
    ) {
        logger.info("VeiculoController::getVeiculo");
        VeiculoRepresentation veiculoRepresentation = new VeiculoRepresentation(veiculoService.findByIdVeiculo(idVeiculo));
        return ResponseEntity.ok(veiculoRepresentation);
    }

    @Operation(summary = "Retorna uma lista de veiculos.", description = "Retorna uma lista de veiculos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna a lista de veiculos.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = VeiculoRepresentation.class))}),
            @ApiResponse(responseCode = "401", description = "Não autorizado;", content = @Content),
            @ApiResponse(responseCode = "404", description = "Lista de veículos não localizada", content = @Content)
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<VeiculoRepresentation>> getAllVeiculo() {
        logger.info("VeiculoController::getAllVeiculo");
        List<Veiculo> veiculos = veiculoService.getAllVeiculo();

        return ResponseEntity.ok(
                veiculos.stream().map(VeiculoRepresentation::new).toList()
        );
    }
}

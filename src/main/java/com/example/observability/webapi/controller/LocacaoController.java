package com.example.observability.webapi.controller;

import com.example.observability.domain.entities.Cliente;
import com.example.observability.domain.entities.Locacao;
import com.example.observability.domain.interfaces.LocacaoService;
import com.example.observability.webapi.representation.ClienteRepresentation;
import com.example.observability.webapi.representation.LocacaoRepresentationSimple;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/locacao")
public class LocacaoController {

    Logger logger = LoggerFactory.getLogger(LocacaoController.class);

    @Autowired
    private LocacaoService locacaoService;

    @Operation(summary = "Retorna uma locação.", description = "Retorna uma locação por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna a locação pesquisada.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = LocacaoRepresentationSimple.class))}),
            @ApiResponse(responseCode = "401", description = "Não autorizado;", content = @Content),
            @ApiResponse(responseCode = "404", description = "Locação não localizado.", content = @Content)
    })
    @GetMapping(value = "/{idLocacao}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LocacaoRepresentationSimple> getLocacao(
            @Parameter(description = "Id da locação")
            @PathVariable(name = "idLocacao") Long idLocacao
    ) {
        logger.info("LocacaoController::getLocacao");
        return Optional.ofNullable(locacaoService.findByIdLocacao(idLocacao))
                .map(result -> new ResponseEntity<LocacaoRepresentationSimple>(
                        new LocacaoRepresentationSimple(result.get()), HttpStatus.OK)
                )
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Retorna uma lista de locações.", description = "Retorna uma lista de locações")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna a lista de locações.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = LocacaoRepresentationSimple.class))}),
            @ApiResponse(responseCode = "401", description = "Não autorizado;", content = @Content),
            @ApiResponse(responseCode = "404", description = "Lista de locações não encontrada.", content = @Content)
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<LocacaoRepresentationSimple>> getAllLocacoes() {
        logger.info("LocacaoController::getAllLocacoes");
        List<Locacao> locacoes = locacaoService.getAllLocacao();

        if (locacoes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(
                locacoes.stream().map(locacao -> new LocacaoRepresentationSimple(locacao)).toList()
        );
    }

    @Operation(summary = "Alugar um veículo.", description = "Aluga um veículo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna o veículo alugado.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = LocacaoRepresentationSimple.class))}),
            @ApiResponse(responseCode = "401", description = "Não autorizado;", content = @Content),
            @ApiResponse(responseCode = "404", description = "Recurso não localizado.", content = @Content)
    })
    @PostMapping(value = "rent/{idCliente}/{idVeiculo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LocacaoRepresentationSimple> setLocacao(
            @Parameter(description = "Id do cliente")
            @PathVariable(name = "idCliente") Long idCliente,
            @Parameter(description = "Id do veiculo")
            @PathVariable(name = "idVeiculo") Long idVeiculo
            ) {
        logger.info("LocacaoController::setLocacao");
        return Optional.ofNullable(locacaoService.setLocacao(idCliente, idVeiculo))
                .map(result -> new ResponseEntity<LocacaoRepresentationSimple>(
                        new LocacaoRepresentationSimple(result), HttpStatus.OK)
                )
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Devolução de uma locação", description = "Devolve o veículo locado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna a locação com baixa.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = LocacaoRepresentationSimple.class))}),
            @ApiResponse(responseCode = "401", description = "Não autorizado;", content = @Content),
            @ApiResponse(responseCode = "404", description = "Recurso não localizado.", content = @Content)
    })
    @PostMapping(value="/devolution/{idLocacao}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LocacaoRepresentationSimple> setDevolucao(
            @Parameter(description = "Id da locação")
            @PathVariable(name = "idLocacao") Long idLocacao
    ) {
        logger.info("LocacaoController::setDevolucao");
        Locacao locacao = locacaoService.setDevolucao(idLocacao);
        return ResponseEntity.ok(new LocacaoRepresentationSimple(locacao));
    }

}

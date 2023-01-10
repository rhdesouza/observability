package com.example.observability.webapi.controller;

import com.example.observability.domain.entities.Cliente;
import com.example.observability.domain.interfaces.ClienteService;
import com.example.observability.webapi.representation.ClienteRepresentation;
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
@RequestMapping("/api/cliente")
public class ClienteController {

    Logger logger = LoggerFactory.getLogger(ClienteController.class);

    @Autowired
    private ClienteService clienteService;

    @Operation(summary = "Retorna um Cliente.", description = "Retorna um cliente por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna o cliente pesquisada.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ClienteRepresentation.class))}),
            @ApiResponse(responseCode = "401", description = "Não autorizado;", content = @Content),
            @ApiResponse(responseCode = "404", description = "Cliente não localizado.", content = @Content)
    })
    @GetMapping(value = "/{idCliente}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ClienteRepresentation> getCliente(
            @Parameter(description = "Id do cliente")
            @PathVariable(name = "idCliente") Long idCliente
    ) {
        logger.info("ClienteController::getCliente");
        return Optional.ofNullable(clienteService.findByIdCliente(idCliente))
                .map(result -> new ResponseEntity<ClienteRepresentation>(
                        new ClienteRepresentation(result.get()), HttpStatus.OK)
                )
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Retorna uma lista de clientes.", description = "Retorna uma lista de clientes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna a lista de clientes.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ClienteRepresentation.class))}),
            @ApiResponse(responseCode = "401", description = "Não autorizado;", content = @Content),
            @ApiResponse(responseCode = "404", description = "Lista de clientes não encontrada.", content = @Content)
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ClienteRepresentation>> getAllClientes() {
        logger.info("ClienteController::getAllClientes");
        List<Cliente> clientes = clienteService.getAllClientes();

        if (clientes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(
                clientes.stream().map(ClienteRepresentation::new).toList()
        );
    }

    @Operation(summary = "Desativa o cliente.", description = "Desativa o cliente por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna o cliente desativado.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ClienteRepresentation.class))}),
            @ApiResponse(responseCode = "401", description = "Não autorizado;", content = @Content),
            @ApiResponse(responseCode = "404", description = "Cliente não localizado.", content = @Content)
    })
    @GetMapping(value = "disable/{idCliente}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ClienteRepresentation> disableCliente(
            @Parameter(description = "Id do cliente")
            @PathVariable(name = "idCliente") Long idCliente
    ) {
        logger.info("ClienteController::disableCliente");
        Cliente cliente = clienteService.disableCliente(idCliente);
        return ResponseEntity.ok(new ClienteRepresentation(cliente));
    }

    @Operation(summary = "Salva Cliente.", description = "Inclui / Edita cliente ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna o cliente salvo.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ClienteRepresentation.class))}),
            @ApiResponse(responseCode = "401", description = "Não autorizado;", content = @Content),
            @ApiResponse(responseCode = "404", description = "Cliente não localizado.", content = @Content)
    })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ClienteRepresentation> save(
            @RequestBody final ClienteRepresentation clienteRepresentation
    ) {
        logger.info("ClienteController::save");
        Cliente cliente = clienteService.save(clienteRepresentation.toDomain());

        return ResponseEntity.ok(new ClienteRepresentation(cliente));
    }

}

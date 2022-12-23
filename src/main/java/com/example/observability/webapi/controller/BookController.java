package com.example.observability.webapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/book")
@Slf4j
public class BookController {

    @Operation(summary = "Retorna uma lista de livros")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<String> getBook() {
        log.info("Service getBook()");
        return List.of("Arquitetura Limpa", "Código Limpo");
    }


}

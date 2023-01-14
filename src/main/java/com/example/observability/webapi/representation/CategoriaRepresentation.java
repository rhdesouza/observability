package com.example.observability.webapi.representation;

import com.example.observability.domain.entities.Categoria;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class CategoriaRepresentation {

    private String categoria;

    private BigDecimal valorDiariaMinima;

    public CategoriaRepresentation(@NotNull Categoria categoriaDomain){
        this.categoria = categoriaDomain.getCategoria();
        this.valorDiariaMinima = categoriaDomain.getValorDiariaMinima();
    }

}

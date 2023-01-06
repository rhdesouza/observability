package com.example.observability.webapi.representation;

import com.example.observability.domain.entities.Categoria;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Data
@AllArgsConstructor
public class CategoriaRepresentation {

    private String categoria;

    public CategoriaRepresentation(@NotNull Categoria categoriaDomain){
        this.categoria = categoriaDomain.getCategoria();
    }

}

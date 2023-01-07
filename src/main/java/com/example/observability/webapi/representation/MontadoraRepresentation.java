package com.example.observability.webapi.representation;

import com.example.observability.domain.entities.Montadora;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Data
@AllArgsConstructor
public class MontadoraRepresentation {

    private String montadora;

    public MontadoraRepresentation(@NotNull Montadora montadoraDomain) {
        this.montadora = montadoraDomain.getMontadora();
    }

}

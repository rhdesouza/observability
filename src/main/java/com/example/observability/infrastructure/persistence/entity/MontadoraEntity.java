package com.example.observability.infrastructure.persistence.entity;

import com.example.observability.domain.entities.Montadora;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "montadora")
@AllArgsConstructor
@NoArgsConstructor
public class MontadoraEntity {

    @Id
    private Integer id;

    @Column(name = "montadora")
    @NotNull(message = "Campo montadora é obrigatório")
    private String montadora;

    public MontadoraEntity(Montadora montadora){
        this.id = montadora.getId();
        this.montadora = montadora.getMontadora();
    }

    public Montadora toDomain() {
        return Montadora.builder()
                .id(this.id)
                .montadora(this.montadora)
                .build();
    }

}

package com.example.observability.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity(name = "montadora")
public class MontadoraEntity {

    @Id
    private Integer id;

    @Column(name = "montadora")
    @NotNull(message = "Campo montadora é obrigatório")
    private String montadora;

}

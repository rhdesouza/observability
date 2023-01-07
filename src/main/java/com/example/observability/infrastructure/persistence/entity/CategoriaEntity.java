package com.example.observability.infrastructure.persistence.entity;

import com.example.observability.domain.entities.Categoria;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "categoria")
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaEntity {

    @Id
    private Integer id;

    @Column(name="categoria")
    @NotNull(message = "Campo categoria é obrigatório")
    private String categoria;

    public CategoriaEntity(Categoria categoriaDomain){
        this.id = categoriaDomain.getId();
        this.categoria = categoriaDomain.getCategoria();
    }

    public Categoria toDomain(){
        return new Categoria(this.id, this.categoria);
    }

}

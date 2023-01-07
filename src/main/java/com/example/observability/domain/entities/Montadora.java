package com.example.observability.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Montadora {
    private Integer id;
    private String montadora;
}

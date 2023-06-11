package com.acelerati.gestionmatricula.domain.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Materia {
    private Long id;
    private String nombre;
    private String descripcion;

}

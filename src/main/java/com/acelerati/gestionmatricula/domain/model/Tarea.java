package com.acelerati.gestionmatricula.domain.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Embeddable;

@Data
@Builder
@Embeddable
public class Tarea {
    private Long id;
    private Curso curso;
    private String descripcion;
}

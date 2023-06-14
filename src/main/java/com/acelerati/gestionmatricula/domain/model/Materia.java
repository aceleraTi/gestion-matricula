package com.acelerati.gestionmatricula.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Materia {
    @NotNull
    private Long id;
    @NotNull
    @Transient
    private Pensum pensum;
    @Transient
    private String nombre;
    @Transient
    private String descripcion;
    @Transient
    private Materia materiaPrerequisito;
}

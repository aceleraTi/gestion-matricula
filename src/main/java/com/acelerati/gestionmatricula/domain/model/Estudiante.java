package com.acelerati.gestionmatricula.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.Transient;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Estudiante {
    private Long id;
    @Transient
    private String nombre;
    @Transient
    private String apellido;
    @Transient
    private String email;
    @Transient
    private String codigo;
}

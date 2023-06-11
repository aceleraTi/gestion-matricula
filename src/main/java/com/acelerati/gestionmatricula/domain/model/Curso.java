package com.acelerati.gestionmatricula.domain.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Curso {

    private Long id;
    private Long idMateria;
    private Long idProfesor;
    private SemestreAcademico semestreAcademico;
    private Integer grupo;
    private String estado;

}

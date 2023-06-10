package com.acelerati.gestionmatricula.domain.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Course {

    private Long id;
    private Long idMateria;
    private Long idProfesor;
    private Long idSemestreAcademico;
    private String grupo;
    private Boolean estado;


}

package com.acelerati.gestionmatricula.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EstudianteCurso {

    private Long id;
    private Estudiante estudiante;
    private Curso curso;
    private Double previo1;
    private Double previo2;
    private Double previo3;
    private Double previo4;
    private Double notaFinal;

}

package com.acelerati.gestionmatricula.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EstudianteCursoTarea {
    private Long id;
    private EstudianteCurso estudianteCurso;
    private Tarea tarea;
    private Double nota;

}

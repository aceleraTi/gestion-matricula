package com.acelerati.gestionmatricula.infraestructure.entitys;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "estudiantes_cursos_tareas")
public class EstudianteCursoTareaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Max(5)
    @Min(0)
    private Double nota;

    //__________________________________Relaciones____________________________

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_estudiante_curso")
    private EstudianteCursoEntity estudianteCurso;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tarea")
    private TareaEntity tarea;

}

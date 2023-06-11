package com.acelerati.gestionmatricula.infraestructure.entitys;


import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@Entity
@Table(name = "estudiantes_cursos_tareas")
public class EstudianteCursoTareaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double nota;

    //__________________________________Relaciones____________________________

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_estudiante_curso")
    private EstudianteCursoEntity estudianteCurso;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tarea")
    private TareaEntity tarea;

}

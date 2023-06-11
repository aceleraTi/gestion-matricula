package com.acelerati.gestionmatricula.infraestructure.entitys;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@Entity
@Table(name = "estudiantes_cursos")
public class EstudianteCursoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double previo1;
    private Double previo2;
    private Double previo3;
    private Double previo4;
    private double notaFinal;


    //__________________________________Relaciones____________________________
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_curso")
    private CursoEntity curso;


    @OneToMany(mappedBy = "estudianteCurso",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<EstudianteCursoTareaEntity> estudianteCursoTareas;

}

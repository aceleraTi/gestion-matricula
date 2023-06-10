package com.acelerati.gestionmatricula.infraestructure.entitys;


import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data

@Entity
@Table(name = "cursos")
public class CursoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    private Long idMateria;

    private Long idProfesor;



    @NotEmpty
    private Integer grupo;

    @NotEmpty
    private String estado;

    //__________________________________Relaciones____________________________

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_semestre_academico")
    private SemestreAcademicoEntity semestreAcademico;

    @OneToMany(mappedBy = "curso",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<HorarioEntity> horarios;

    @OneToMany(mappedBy = "curso",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<EstudianteCursoEntity> estudianteCursos;

    @OneToMany(mappedBy = "curso",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<TareaEntity> tareas;


}

package com.acelerati.gestionmatricula.infraestructure.entitys;


import com.acelerati.gestionmatricula.domain.model.Materia;
import com.acelerati.gestionmatricula.domain.model.Profesor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cursos")
public class CursoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "id_materia"))
    @NotNull
    private Materia materia;
    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "id_profesor"))
    private Profesor profesor;
    @NotNull
    private Integer grupo;

    @NotEmpty
    private String estado;

    //__________________________________Relaciones____________________________

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_semestre_academico")
    private SemestreAcademicoEntity semestreAcademicoEntity;

    @OneToMany(mappedBy = "curso",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<HorarioEntity> horarios;

    @OneToMany(mappedBy = "curso",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<EstudianteCursoEntity> estudianteCursos;

    @OneToMany(mappedBy = "curso",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<TareaEntity> tareas;
    //-------------------------------------------------------------------------------

    @PrePersist
    protected void onCreate(){
        this.estado = "En Curso";
    }

}

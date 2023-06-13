package com.acelerati.gestionmatricula.infraestructure.entitys;

import com.acelerati.gestionmatricula.domain.model.Estudiante;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "estudiantes_cursos")
public class EstudianteCursoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "id_estudiante"))
    @NotNull
    private Estudiante estudiante;
    @Max(5)
    @Min(0)
    private Double previo1;
    @Max(5)
    @Min(0)
    private Double previo2;
    @Max(5)
    @Min(0)
    private Double previo3;
    @Max(5)
    @Min(0)
    private Double previo4;
    @Max(5)
    @Min(0)
    private Double notaFinal;


    //__________________________________Relaciones____________________________
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_curso")
    private CursoEntity curso;


    @OneToMany(mappedBy = "estudianteCurso",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<EstudianteCursoTareaEntity> estudianteCursoTareas;

}

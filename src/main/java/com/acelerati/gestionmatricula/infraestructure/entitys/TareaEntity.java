package com.acelerati.gestionmatricula.infraestructure.entitys;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@Entity
@Table(name = "tareas")
public class TareaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descripcion;

    //__________________________________Relaciones____________________________

    @OneToMany(mappedBy = "tarea",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<EstudianteCursoTareaEntity> estudianteCursoTareas;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_curso")
    private CursoEntity curso;


}

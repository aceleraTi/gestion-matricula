package com.acelerati.gestionmatricula.infraestructure.entitys;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Builder
@Entity
@Table(name = "tareas")
public class TareaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 300, min = 100,message = "Cada tarea debe tener una descripcion de minimo 100 caracteres y maximo 300")
    private String descripcion;

    //__________________________________Relaciones____________________________

    @OneToMany(mappedBy = "tarea",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<EstudianteCursoTareaEntity> estudianteCursoTareas;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_curso")
    private CursoEntity curso;


}

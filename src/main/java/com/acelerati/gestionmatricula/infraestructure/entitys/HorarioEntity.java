package com.acelerati.gestionmatricula.infraestructure.entitys;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "horarios")
public class HorarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private LocalTime horaInicio;
    @NotNull
    private LocalTime horaFin;
    @NotNull
    private String dia;
    @NotNull
    private String link;


    //__________________________________Relaciones____________________________

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_curso ")
    private CursoEntity curso;



}

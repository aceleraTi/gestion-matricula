package com.acelerati.gestionmatricula.infraestructure.entitys;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalTime;

@Data
@Builder
@Entity
@Table(name = "horarios")
public class HorarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalTime HoraInicio;

    private LocalTime HoraFin;

    private String dia;

    private String link;


    //__________________________________Relaciones____________________________

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_curso ")
    private CursoEntity curso;



}

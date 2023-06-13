package com.acelerati.gestionmatricula.domain.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalTime;

@Data
@Builder
public class Horario {

    private Long id;
    private Curso curso;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private String dia;
    private String link;




}

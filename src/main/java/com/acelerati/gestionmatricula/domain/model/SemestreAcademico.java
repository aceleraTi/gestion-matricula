package com.acelerati.gestionmatricula.domain.model;

import com.acelerati.gestionmatricula.infraestructure.entitys.SemestreAcademicoEntity;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class SemestreAcademico  {

    private Long id;
    private Integer numero;
    private Integer año;
    private Date fechaInicio;
    private Date fechaFin;



}

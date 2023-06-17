package com.acelerati.gestionmatricula.infraestructure.rest.mappers;

import com.acelerati.gestionmatricula.domain.model.SemestreAcademico;
import com.acelerati.gestionmatricula.infraestructure.entitys.SemestreAcademicoEntity;

import java.util.Optional;

public class SemestreAcademicoMapper {

    public static SemestreAcademico alSemestreAcademico(SemestreAcademicoEntity semestreAcademicoEntity){

        return new SemestreAcademico(semestreAcademicoEntity.getId(),semestreAcademicoEntity.getNumero(),
                semestreAcademicoEntity.getAño(),semestreAcademicoEntity.getFechaInicio(),
                semestreAcademicoEntity.getFechaFin());

    }

    public static SemestreAcademicoEntity alSemestreAcademicoEntity(SemestreAcademico semestreAcademico){

        return SemestreAcademicoEntity.builder()
                .id(Optional.ofNullable(semestreAcademico.getId()).orElse(0L))
                .numero(semestreAcademico.getNumero())
                .año(semestreAcademico.getAño())
                .fechaInicio(semestreAcademico.getFechaInicio())
                .fechaFin(semestreAcademico.getFechaFin())
                .build();

    }
}

package com.acelerati.gestionmatricula.infraestructure.rest.mappers;

import com.acelerati.gestionmatricula.domain.model.SemestreAcademico;
import com.acelerati.gestionmatricula.infraestructure.entitys.SemestreAcademicoEntity;

public class SemestreAcademicoMapper {

    public static SemestreAcademico alSemestreAcademico(SemestreAcademicoEntity semestreAcademicoEntity){

        return SemestreAcademico.builder()
                .id(semestreAcademicoEntity.getId())
                .numero(semestreAcademicoEntity.getNumero())
                .año(semestreAcademicoEntity.getAño())
                .fechaInicio(semestreAcademicoEntity.getFechaInicio())
                .fechaFin(semestreAcademicoEntity.getFechaFin())
                .build();

    }

    public static SemestreAcademicoEntity alSemestreAcademicoEntity(SemestreAcademico semestreAcademico){

        return SemestreAcademicoEntity.builder()
                .id(semestreAcademico.getId())
                .numero(semestreAcademico.getNumero())
                .año(semestreAcademico.getAño())
                .fechaInicio(semestreAcademico.getFechaInicio())
                .fechaFin(semestreAcademico.getFechaFin())
                .build();

    }
}

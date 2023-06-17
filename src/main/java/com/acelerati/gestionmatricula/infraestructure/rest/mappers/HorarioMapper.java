package com.acelerati.gestionmatricula.infraestructure.rest.mappers;

import com.acelerati.gestionmatricula.domain.model.Horario;
import com.acelerati.gestionmatricula.infraestructure.entitys.HorarioEntity;

import java.util.Optional;

import static com.acelerati.gestionmatricula.infraestructure.rest.mappers.CursoMapper.alCurso;
import static com.acelerati.gestionmatricula.infraestructure.rest.mappers.CursoMapper.alCursoEntity;

public class HorarioMapper {



    public static Horario alHorario(HorarioEntity horarioEntity){

        return new Horario(horarioEntity.getId(),alCurso(horarioEntity.getCurso()),horarioEntity.getHoraInicio(),
                       horarioEntity.getHoraFin(), horarioEntity.getDia(),horarioEntity.getLink());
    }

    public static HorarioEntity alHorarioEntity(Horario horario){
        return HorarioEntity.builder()
                .id(horario.getId())
                .curso(alCursoEntity(horario.getCurso()))
                .horaInicio(horario.getHoraInicio())
                .horaFin(horario.getHoraFin())
                .dia(horario.getDia())
                .link(horario.getLink())
                .build();
    }


}

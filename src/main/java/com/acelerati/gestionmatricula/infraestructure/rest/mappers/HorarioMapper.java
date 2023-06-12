package com.acelerati.gestionmatricula.infraestructure.rest.mappers;

import com.acelerati.gestionmatricula.domain.model.Horario;
import com.acelerati.gestionmatricula.infraestructure.entitys.HorarioEntity;

import static com.acelerati.gestionmatricula.infraestructure.rest.mappers.CursoMapper.alCurso;
import static com.acelerati.gestionmatricula.infraestructure.rest.mappers.CursoMapper.alCursoEntity;

public class HorarioMapper {



    public static Horario alHorario(HorarioEntity horarioEntity){
        return Horario.builder()
                .id(horarioEntity.getId())
                .curso(alCurso(horarioEntity.getCurso()))
                .horaInicio(horarioEntity.getHoraInicio())
                .horaFin(horarioEntity.getHoraFin())
                .dia(horarioEntity.getDia())
                .link(horarioEntity.getLink())
                .build();
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

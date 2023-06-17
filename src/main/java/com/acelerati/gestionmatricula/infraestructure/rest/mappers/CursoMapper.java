package com.acelerati.gestionmatricula.infraestructure.rest.mappers;

import com.acelerati.gestionmatricula.domain.model.Curso;
import com.acelerati.gestionmatricula.infraestructure.entitys.CursoEntity;

import java.util.Optional;

import static com.acelerati.gestionmatricula.infraestructure.rest.mappers.SemestreAcademicoMapper.alSemestreAcademico;
import static com.acelerati.gestionmatricula.infraestructure.rest.mappers.SemestreAcademicoMapper.alSemestreAcademicoEntity;

public class CursoMapper {

    public static Curso alCurso(CursoEntity cursoEntity){

        return new Curso(cursoEntity.getId(),cursoEntity.getMateria(),cursoEntity.getProfesor(),
                alSemestreAcademico(cursoEntity.getSemestreAcademicoEntity()),cursoEntity.getGrupo(),
                cursoEntity.getEstado());

    }

    public static CursoEntity alCursoEntity(Curso curso){

        return CursoEntity.builder()
                .id(Optional.ofNullable(curso.getId()).orElse(0L))
                .materia(curso.getMateria())
                .profesor(curso.getProfesor())
                .semestreAcademicoEntity(alSemestreAcademicoEntity(curso.getSemestreAcademico()))
                .grupo(curso.getGrupo())
                .estado(curso.getEstado())
                .build();
    }

}

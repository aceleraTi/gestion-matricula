package com.acelerati.gestionmatricula.infraestructure.rest.mappers;

import com.acelerati.gestionmatricula.domain.model.Curso;
import com.acelerati.gestionmatricula.infraestructure.entitys.CursoEntity;

import static com.acelerati.gestionmatricula.infraestructure.rest.mappers.SemestreAcademicoMapper.alSemestreAcademico;
import static com.acelerati.gestionmatricula.infraestructure.rest.mappers.SemestreAcademicoMapper.alSemestreAcademicoEntity;

public class CursoMapper {

    public static Curso alCurso(CursoEntity cursoEntity){

        return Curso.builder()
                .id(cursoEntity.getId())
                .materia(cursoEntity.getMateria())
                .profesor(cursoEntity.getProfesor())
                .semestreAcademico(alSemestreAcademico(cursoEntity.getSemestreAcademicoEntity()))
                .grupo(cursoEntity.getGrupo())
                .estado(cursoEntity.getEstado())
                .build();

    }

    public static CursoEntity alCursoEntity(Curso curso){

        return CursoEntity.builder()
                .id(curso.getId())
                .materia(curso.getMateria())
                .profesor(curso.getProfesor())
                .semestreAcademicoEntity(alSemestreAcademicoEntity(curso.getSemestreAcademico()))
                .grupo(curso.getGrupo())
                .estado(curso.getEstado())
                .build();
    }

}

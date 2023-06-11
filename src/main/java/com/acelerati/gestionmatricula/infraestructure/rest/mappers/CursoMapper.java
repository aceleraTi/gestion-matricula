package com.acelerati.gestionmatricula.infraestructure.rest.mappers;

import com.acelerati.gestionmatricula.domain.model.Curso;
import com.acelerati.gestionmatricula.domain.model.SemestreAcademico;
import com.acelerati.gestionmatricula.infraestructure.entitys.CursoEntity;

public class CursoMapper {

    public static Curso alCurso(CursoEntity cursoEntity){

        return Curso.builder()
                .id(cursoEntity.getId())
                .idMateria(cursoEntity.getIdMateria())
                .idProfesor(cursoEntity.getIdProfesor())
                .grupo(cursoEntity.getGrupo())
                .estado(cursoEntity.getEstado())
                .build();

    }

    public static CursoEntity alCursoEntity(Curso curso){

        return CursoEntity.builder()
                .id(curso.getId())
                .idMateria(curso.getIdMateria())
                .idProfesor(curso.getIdProfesor())
                .grupo(curso.getGrupo())
                .estado(curso.getEstado())
                .build();
    }

}

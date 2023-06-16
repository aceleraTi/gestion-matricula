package com.acelerati.gestionmatricula.infraestructure.rest.mappers;

import com.acelerati.gestionmatricula.domain.model.EstudianteCurso;
import com.acelerati.gestionmatricula.infraestructure.entitys.EstudianteCursoEntity;

import static com.acelerati.gestionmatricula.infraestructure.rest.mappers.CursoMapper.alCurso;
import static com.acelerati.gestionmatricula.infraestructure.rest.mappers.CursoMapper.alCursoEntity;

public class EstudianteCursoMapper {
    public  static EstudianteCurso alEstudianteCurso(EstudianteCursoEntity estudianteCursoEntity){
           return new EstudianteCurso(estudianteCursoEntity.getId(),
                estudianteCursoEntity.getEstudiante(),
                alCurso(estudianteCursoEntity.getCurso()),
                estudianteCursoEntity.getPrevio1(),
                estudianteCursoEntity.getPrevio2(),
                estudianteCursoEntity.getPrevio3(),
                estudianteCursoEntity.getPrevio4(),
                estudianteCursoEntity.getNotaFinal());
    }

    public static EstudianteCursoEntity alEstudianteCursoEntity(EstudianteCurso estudianteCurso){
        return EstudianteCursoEntity.builder()
                .id(estudianteCurso.getId())
                .estudiante(estudianteCurso.getEstudiante())
                .curso(alCursoEntity(estudianteCurso.getCurso()))
                .previo1(estudianteCurso.getPrevio1())
                .previo2(estudianteCurso.getPrevio2())
                .previo3(estudianteCurso.getPrevio3())
                .previo4(estudianteCurso.getPrevio4())
                .notaFinal(estudianteCurso.getNotaFinal())
                .build();
    }
}

package com.acelerati.gestionmatricula.infraestructure.rest.mappers;

import com.acelerati.gestionmatricula.domain.model.EstudianteCurso;
import com.acelerati.gestionmatricula.infraestructure.entitys.EstudianteCursoEntity;

import static com.acelerati.gestionmatricula.infraestructure.rest.mappers.CursoMapper.alCurso;
import static com.acelerati.gestionmatricula.infraestructure.rest.mappers.CursoMapper.alCursoEntity;

public class EstudianteCursoMapper {
    public  static EstudianteCurso alEstudianteCurso(EstudianteCursoEntity estudianteCursoEntity){
        return EstudianteCurso.builder()
                .id(estudianteCursoEntity.getId())
                .estudiante(estudianteCursoEntity.getEstudiante())
                .curso(alCurso(estudianteCursoEntity.getCurso()))
                .previo1(estudianteCursoEntity.getPrevio1())
                .previo2(estudianteCursoEntity.getPrevio2())
                .previo3(estudianteCursoEntity.getPrevio3())
                .previo4(estudianteCursoEntity.getPrevio4())
                .notaFinal(estudianteCursoEntity.getNotaFinal())
                .build();
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

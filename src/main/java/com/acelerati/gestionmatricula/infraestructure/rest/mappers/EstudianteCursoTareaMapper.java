package com.acelerati.gestionmatricula.infraestructure.rest.mappers;

import com.acelerati.gestionmatricula.domain.model.EstudianteCursoTarea;
import com.acelerati.gestionmatricula.infraestructure.entitys.EstudianteCursoTareaEntity;

import static com.acelerati.gestionmatricula.infraestructure.rest.mappers.EstudianteCursoMapper.alEstudianteCurso;
import static com.acelerati.gestionmatricula.infraestructure.rest.mappers.EstudianteCursoMapper.alEstudianteCursoEntity;
import static com.acelerati.gestionmatricula.infraestructure.rest.mappers.TareaMapper.alaTarea;
import static com.acelerati.gestionmatricula.infraestructure.rest.mappers.TareaMapper.alaTareaEntity;

public class EstudianteCursoTareaMapper {
    public static EstudianteCursoTareaEntity alEstudianteCursoTareaEntity(EstudianteCursoTarea estudianteCursoTarea){
        return EstudianteCursoTareaEntity.builder()
                .id(estudianteCursoTarea.getId())
                .estudianteCurso(alEstudianteCursoEntity(estudianteCursoTarea.getEstudianteCurso()))
                .tarea(alaTareaEntity(estudianteCursoTarea.getTarea()))
                .nota(estudianteCursoTarea.getNota())
                .build();

    }
    public static EstudianteCursoTarea alEstudianteCursoTarea(EstudianteCursoTareaEntity estudianteCursoTareaEntity){

        return new EstudianteCursoTarea(estudianteCursoTareaEntity.getId(),
               alEstudianteCurso(estudianteCursoTareaEntity.getEstudianteCurso()),
                alaTarea(estudianteCursoTareaEntity.getTarea()),
                estudianteCursoTareaEntity.getNota());

    }

}

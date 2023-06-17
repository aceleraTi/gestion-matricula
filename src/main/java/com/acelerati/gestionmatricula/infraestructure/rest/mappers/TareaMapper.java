package com.acelerati.gestionmatricula.infraestructure.rest.mappers;

import com.acelerati.gestionmatricula.domain.model.Tarea;
import com.acelerati.gestionmatricula.infraestructure.entitys.TareaEntity;

import java.util.Optional;

import static com.acelerati.gestionmatricula.infraestructure.rest.mappers.CursoMapper.alCurso;
import static com.acelerati.gestionmatricula.infraestructure.rest.mappers.CursoMapper.alCursoEntity;

public class TareaMapper {

    public static Tarea alaTarea(TareaEntity tareaEntity){
        return new Tarea(tareaEntity.getId(), alCurso(tareaEntity.getCurso()), tareaEntity.getDescripcion());
    }

    public static TareaEntity alaTareaEntity(Tarea tarea){
        return TareaEntity.builder()
                .id(tarea.getId())
                .curso(alCursoEntity(tarea.getCurso()))
                .descripcion(tarea.getDescripcion())
                .build();
    }
}

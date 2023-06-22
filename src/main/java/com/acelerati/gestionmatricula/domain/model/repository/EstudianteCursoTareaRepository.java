package com.acelerati.gestionmatricula.domain.model.repository;

import com.acelerati.gestionmatricula.infraestructure.entitys.EstudianteCursoTareaEntity;

import java.util.Optional;

public interface EstudianteCursoTareaRepository {
    EstudianteCursoTareaEntity subirNota(EstudianteCursoTareaEntity estudianteCursoTareaEntity);
    Double notaTarea(Long idTarea,Long idEstudianteCurso);
   Optional<EstudianteCursoTareaEntity> existeEstudianteCursoNotaTarea(Long idTarea, Long idEstudianteCurso);
}

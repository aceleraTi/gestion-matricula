package com.acelerati.gestionmatricula.infraestructure.driven_adapters.persistence.interfaces.jpa_repository;

import com.acelerati.gestionmatricula.infraestructure.entitys.EstudianteCursoTareaEntity;

public interface EstudianteCursoTareaRepository {
    EstudianteCursoTareaEntity subirNota(EstudianteCursoTareaEntity estudianteCursoTareaEntity);
    Double notaTarea(Long idTarea,Long idEstudianteCurso);
}

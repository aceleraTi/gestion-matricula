package com.acelerati.gestionmatricula.domain.persistence;

import com.acelerati.gestionmatricula.infraestructure.entitys.EstudianteCursoTareaEntity;

public interface EstudianteCursoTareaRepository {
    EstudianteCursoTareaEntity subirNota(EstudianteCursoTareaEntity estudianteCursoTareaEntity);
}

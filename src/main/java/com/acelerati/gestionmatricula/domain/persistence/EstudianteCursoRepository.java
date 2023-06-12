package com.acelerati.gestionmatricula.domain.persistence;

import com.acelerati.gestionmatricula.domain.model.Materia;
import com.acelerati.gestionmatricula.infraestructure.entitys.EstudianteCursoEntity;

public interface EstudianteCursoRepository {
    EstudianteCursoEntity registrarCurso(EstudianteCursoEntity estudianteCurso, Materia materia);
}

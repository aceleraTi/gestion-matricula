package com.acelerati.gestionmatricula.domain.persistence;

import com.acelerati.gestionmatricula.domain.model.Estudiante;
import com.acelerati.gestionmatricula.domain.model.Materia;
import com.acelerati.gestionmatricula.infraestructure.entitys.EstudianteCursoEntity;

import java.util.List;

public interface EstudianteCursoRepository {
    EstudianteCursoEntity registrarCurso(EstudianteCursoEntity estudianteCurso, Materia materia);
    List<EstudianteCursoEntity> ListarCursosEstudiante(Estudiante estudiante);

    EstudianteCursoEntity findByEstudianteCursoEntityId(Long id);

}

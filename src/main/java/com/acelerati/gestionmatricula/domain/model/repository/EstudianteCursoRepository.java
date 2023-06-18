package com.acelerati.gestionmatricula.domain.model.repository;

import com.acelerati.gestionmatricula.domain.model.Estudiante;

import com.acelerati.gestionmatricula.infraestructure.entitys.CursoEntity;
import com.acelerati.gestionmatricula.infraestructure.entitys.EstudianteCursoEntity;

import java.util.List;
import java.util.Optional;

public interface EstudianteCursoRepository {
    EstudianteCursoEntity registrarCurso(EstudianteCursoEntity estudianteCurso);
    List<EstudianteCursoEntity> ListarCursosEstudiante(Estudiante estudiante);

   Optional<EstudianteCursoEntity> findByEstudianteCursoEntityId(Long id);

    EstudianteCursoEntity actualizarCursoEstudiante(EstudianteCursoEntity estudianteCursoEntity);

    List<EstudianteCursoEntity> findByCurso(CursoEntity cursoEntity);

    List<EstudianteCursoEntity> guardarEstudiantesCursos(List<EstudianteCursoEntity> estudianteCursoEntities);

    Optional<EstudianteCursoEntity> findByEstudianteIdAndCursoId(Long idEstudiante, Long idCurso);

}

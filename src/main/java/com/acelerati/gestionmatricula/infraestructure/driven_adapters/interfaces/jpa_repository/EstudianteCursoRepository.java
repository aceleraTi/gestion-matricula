package com.acelerati.gestionmatricula.infraestructure.driven_adapters.interfaces.jpa_repository;

import com.acelerati.gestionmatricula.domain.model.Estudiante;
import com.acelerati.gestionmatricula.domain.model.Materia;
import com.acelerati.gestionmatricula.infraestructure.entitys.CursoEntity;
import com.acelerati.gestionmatricula.infraestructure.entitys.EstudianteCursoEntity;

import java.util.List;

public interface EstudianteCursoRepository {
    EstudianteCursoEntity registrarCurso(EstudianteCursoEntity estudianteCurso);
    List<EstudianteCursoEntity> ListarCursosEstudiante(Estudiante estudiante);

    EstudianteCursoEntity findByEstudianteCursoEntityId(Long id);

    EstudianteCursoEntity actualizarCursoEstudiante(EstudianteCursoEntity estudianteCursoEntity);

    List<EstudianteCursoEntity> findByCurso(CursoEntity cursoEntity);

    List<EstudianteCursoEntity> guardarEstudiantesCursos(List<EstudianteCursoEntity> estudianteCursoEntities);

    EstudianteCursoEntity findByEstudianteIdAndCursoId(Long idEstudiante, Long idCurso);

}

package com.acelerati.gestionmatricula.application.service.interfaces;

import com.acelerati.gestionmatricula.domain.model.Curso;
import com.acelerati.gestionmatricula.domain.model.Estudiante;
import com.acelerati.gestionmatricula.domain.model.EstudianteCurso;
import com.acelerati.gestionmatricula.domain.model.Materia;
import com.acelerati.gestionmatricula.infraestructure.entitys.EstudianteCursoEntity;

import java.util.List;

public interface EstudianteCursoService {
    EstudianteCurso registrarCurso(EstudianteCurso estudianteCurso, Materia materia);

    EstudianteCurso actualizarCursoEstudiante(EstudianteCurso estudianteCurso);


    List<Curso> listarEstudianteCurso (Estudiante estudiante);

    EstudianteCurso findByEstudianteCursoId(Long id);

    List<EstudianteCurso> findByCurso(Curso curso);

    List<EstudianteCurso> guardarEstudiantesCursos(List<EstudianteCurso> estudianteCurso);


}

package com.acelerati.gestionmatricula.application.service.interfaces;

import com.acelerati.gestionmatricula.domain.model.Curso;
import com.acelerati.gestionmatricula.domain.model.Estudiante;
import com.acelerati.gestionmatricula.domain.model.EstudianteCurso;
import com.acelerati.gestionmatricula.domain.model.Materia;

import java.util.List;

public interface EstudianteCursoService {
    EstudianteCurso registrarCurso(EstudianteCurso estudianteCurso, Materia materia);

    EstudianteCurso actualizarCursoEstudiante(EstudianteCurso estudianteCurso);


    List<Curso> listarEstudianteCurso (Estudiante estudiante);

    EstudianteCurso findByEstudianteCursoId(Long id);


}

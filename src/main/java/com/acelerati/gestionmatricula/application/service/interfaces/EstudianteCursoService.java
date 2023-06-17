package com.acelerati.gestionmatricula.application.service.interfaces;

import com.acelerati.gestionmatricula.domain.model.Curso;
import com.acelerati.gestionmatricula.domain.model.Estudiante;
import com.acelerati.gestionmatricula.domain.model.EstudianteCurso;
import com.acelerati.gestionmatricula.domain.model.Profesor;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface EstudianteCursoService {


    List<EstudianteCurso> findByCurso(Curso curso);

    EstudianteCurso registrarseEstudianteCurso(Long idCurso, Estudiante estudiante);

    List<String>listaHorario(HttpSession session);

    EstudianteCurso subirNota(EstudianteCurso estudianteCurso, Profesor profesor);
}

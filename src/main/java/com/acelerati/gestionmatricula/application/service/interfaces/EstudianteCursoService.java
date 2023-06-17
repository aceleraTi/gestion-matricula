package com.acelerati.gestionmatricula.application.service.interfaces;

import com.acelerati.gestionmatricula.domain.model.Curso;
import com.acelerati.gestionmatricula.domain.model.Estudiante;
import com.acelerati.gestionmatricula.domain.model.EstudianteCurso;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface EstudianteCursoService {
   // EstudianteCurso registrarCurso(EstudianteCurso estudianteCurso);

  //  EstudianteCurso actualizarCursoEstudiante(EstudianteCurso estudianteCurso);

  //  List<EstudianteCurso> listarEstudianteCursos(Estudiante estudiante);

   // EstudianteCurso findByEstudianteCursoId(Long id);

    List<EstudianteCurso> findByCurso(Curso curso);

    //List<EstudianteCurso> guardarEstudiantesCursos(List<EstudianteCurso> estudianteCurso);

    EstudianteCurso registrarseEstudianteCurso(Long idCurso, HttpSession session);

    List<String>listaHorario(HttpSession session);

    EstudianteCurso subirNota(EstudianteCurso estudianteCurso, HttpSession session);
}

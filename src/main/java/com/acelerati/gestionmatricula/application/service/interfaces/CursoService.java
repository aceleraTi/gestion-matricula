package com.acelerati.gestionmatricula.application.service.interfaces;

import com.acelerati.gestionmatricula.domain.model.Curso;
import com.acelerati.gestionmatricula.domain.model.Profesor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpSession;

public interface CursoService {

    Curso create(Curso curso, HttpSession session);
    Curso findById(Long id);
    Curso asignarProfesor(Long idCurso, Long idProfesor , HttpSession session);
    Page<Curso> findByProfesor(Profesor profesor, Pageable pageable);
    Curso cerrarCurso(Long idCurso, HttpSession session);
}

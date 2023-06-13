package com.acelerati.gestionmatricula.application.service.interfaces;

import com.acelerati.gestionmatricula.domain.model.Curso;
import com.acelerati.gestionmatricula.domain.model.Profesor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CursoService {

    Curso create(Curso curso);
    Curso findById(Long id);

    Curso update(Curso curso);

    Page<Curso> findByProfesor(Profesor profesor, Pageable pageable);

}

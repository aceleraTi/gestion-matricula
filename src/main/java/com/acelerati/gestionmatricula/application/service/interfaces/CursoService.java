package com.acelerati.gestionmatricula.application.service.interfaces;

import com.acelerati.gestionmatricula.domain.model.Curso;
import com.acelerati.gestionmatricula.domain.model.Materia;
import com.acelerati.gestionmatricula.domain.model.Profesor;
import com.acelerati.gestionmatricula.infraestructure.entitys.CursoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CursoService {

    Curso create(Curso curso);
    Curso findById(Long id);
    Curso update(Curso curso);
    Page<Curso> findByProfesor(Profesor profesor, Pageable pageable);

    List<Curso> listCursos(Materia materia);

}

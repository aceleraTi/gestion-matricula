package com.acelerati.gestionmatricula.domain.persistence;


import com.acelerati.gestionmatricula.domain.model.Profesor;
import com.acelerati.gestionmatricula.infraestructure.entitys.CursoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CursoRepository {

    CursoEntity save(CursoEntity cursoEntity);
    CursoEntity update(CursoEntity cursoEntity);
    CursoEntity findById(Long id);

    Page<CursoEntity> findByProfesor(Profesor profesor, Pageable pageable);






}

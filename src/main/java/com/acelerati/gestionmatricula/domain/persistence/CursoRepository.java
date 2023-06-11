package com.acelerati.gestionmatricula.domain.persistence;

import com.acelerati.gestionmatricula.domain.model.Curso;
import com.acelerati.gestionmatricula.infraestructure.entitys.CursoEntity;

public interface CursoRepository {

    CursoEntity save(CursoEntity cursoEntity);




}

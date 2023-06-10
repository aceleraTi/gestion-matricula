package com.acelerati.gestionmatricula.infraestructure.adapters.interfaces;

import com.acelerati.gestionmatricula.infraestructure.entitys.EstudianteCursoEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstudianteCursoRepositoryMysql extends CrudRepository<EstudianteCursoEntity,Long> {
}

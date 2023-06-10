package com.acelerati.gestionmatricula.infraestructure.adapters.interfaces;

import com.acelerati.gestionmatricula.infraestructure.entitys.EstudianteCursoTareaEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstudianteCursoTareaRepositoryMySql extends CrudRepository<EstudianteCursoTareaEntity,Long> {
}

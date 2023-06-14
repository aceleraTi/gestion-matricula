package com.acelerati.gestionmatricula.infraestructure.adapters.interfaces;

import com.acelerati.gestionmatricula.infraestructure.entitys.CursoEntity;
import com.acelerati.gestionmatricula.infraestructure.entitys.TareaEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TareaRepositoryMySql extends CrudRepository<TareaEntity, Long> {

    int countByCurso(CursoEntity curso);

    List<TareaEntity> findByCursoId(Long id);


}

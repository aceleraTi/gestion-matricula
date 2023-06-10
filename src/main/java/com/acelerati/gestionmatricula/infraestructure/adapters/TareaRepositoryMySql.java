package com.acelerati.gestionmatricula.infraestructure.adapters;

import com.acelerati.gestionmatricula.infraestructure.entitys.TareaEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TareaRepositoryMySql extends CrudRepository<TareaEntity, Long> {

}

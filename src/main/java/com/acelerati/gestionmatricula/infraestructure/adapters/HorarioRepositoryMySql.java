package com.acelerati.gestionmatricula.infraestructure.adapters;

import com.acelerati.gestionmatricula.infraestructure.entitys.HorarioEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HorarioRepositoryMySql extends CrudRepository<HorarioEntity,Long> {
}

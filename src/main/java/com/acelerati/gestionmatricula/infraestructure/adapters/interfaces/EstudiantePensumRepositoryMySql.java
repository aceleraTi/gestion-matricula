package com.acelerati.gestionmatricula.infraestructure.adapters.interfaces;

import com.acelerati.gestionmatricula.infraestructure.entitys.EstudiantePensumEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstudiantePensumRepositoryMySql extends CrudRepository<EstudiantePensumEntity,Long> {
}

package com.acelerati.gestionmatricula.infraestructure.driven_adapters.interfaces;

import com.acelerati.gestionmatricula.infraestructure.entitys.CursoEntity;
import com.acelerati.gestionmatricula.infraestructure.entitys.HorarioEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HorarioRepositoryMySql extends CrudRepository<HorarioEntity,Long> {

    int countByCurso(CursoEntity cursoEntity);
    List<HorarioEntity> findByCurso(CursoEntity cursoEntity);

}

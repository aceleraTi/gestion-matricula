package com.acelerati.gestionmatricula.infraestructure.driven_adapters.persistence.interfaces;

import com.acelerati.gestionmatricula.infraestructure.entitys.SemestreAcademicoEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SemestreAcademicoRepositoryMySql extends CrudRepository<SemestreAcademicoEntity,Long> {
}

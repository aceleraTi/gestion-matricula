package com.acelerati.gestionmatricula.infraestructure.adapters.interfaces;

import com.acelerati.gestionmatricula.infraestructure.entitys.CursoEntity;
import com.acelerati.gestionmatricula.infraestructure.entitys.SemestreAcademicoEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CursoRepositoryMySql extends CrudRepository<CursoEntity,Long> {
   // CursoEntity save(CursoEntity cursoEntity);

   long countByGrupoAndIdMateriaAndSemestreAcademicoEntity(Integer grupo, Long idMateria,
                                                     SemestreAcademicoEntity idSemestreAcademico);
}

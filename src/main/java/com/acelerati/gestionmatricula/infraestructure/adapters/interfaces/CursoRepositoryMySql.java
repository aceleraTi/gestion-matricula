package com.acelerati.gestionmatricula.infraestructure.adapters.interfaces;

import com.acelerati.gestionmatricula.domain.model.Materia;
import com.acelerati.gestionmatricula.domain.model.Profesor;
import com.acelerati.gestionmatricula.infraestructure.entitys.CursoEntity;
import com.acelerati.gestionmatricula.infraestructure.entitys.SemestreAcademicoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CursoRepositoryMySql  extends PagingAndSortingRepository<CursoEntity,Long> {


   Optional<CursoEntity> findById(Long id);

   int countByGrupoAndMateriaAndSemestreAcademicoEntity(Integer grupo, Materia materia,
                                                     SemestreAcademicoEntity SemestreAcademico);

   int countByProfesorAndEstado(Profesor profesor, String estado);

  CursoEntity findByIdAndProfesorAndEstado(Long id,Profesor profesor,String estado);

   Page<CursoEntity> findByProfesor(Profesor profesor, Pageable pageable);

   Optional<CursoEntity> findByIdAndMateria(Long id, Materia materia);

}

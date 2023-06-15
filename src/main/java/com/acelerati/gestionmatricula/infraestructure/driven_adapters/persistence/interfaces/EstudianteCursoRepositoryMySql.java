package com.acelerati.gestionmatricula.infraestructure.driven_adapters.persistence.interfaces;

import com.acelerati.gestionmatricula.domain.model.Estudiante;
import com.acelerati.gestionmatricula.infraestructure.entitys.CursoEntity;
import com.acelerati.gestionmatricula.infraestructure.entitys.EstudianteCursoEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EstudianteCursoRepositoryMySql extends CrudRepository<EstudianteCursoEntity,Long> {

    int countByCursoAndEstudiante(CursoEntity cursoEntity, Estudiante estudiante);
    //@Query(value="SELECT * FROM tasks WHERE completed='false' ORDER BY priority ASC ",nativeQuery = true)
    List<EstudianteCursoEntity> findByEstudiante(Estudiante estudiante);

    List<EstudianteCursoEntity> findByCurso(CursoEntity cursoEntity);

}

package com.acelerati.gestionmatricula.infraestructure.adapters.interfaces;

import com.acelerati.gestionmatricula.domain.model.Estudiante;
import com.acelerati.gestionmatricula.domain.model.EstudiantePensum;
import com.acelerati.gestionmatricula.domain.model.Pensum;
import com.acelerati.gestionmatricula.infraestructure.entitys.EstudiantePensumEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface EstudiantePensumRepositoryMySql extends CrudRepository<EstudiantePensumEntity,Long> {
    int countByEstudiante(Estudiante estudiante);
    List<EstudiantePensumEntity> findByEstudianteAndPensum(Estudiante estudiante, Pensum pensum);
}

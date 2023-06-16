package com.acelerati.gestionmatricula.infraestructure.driven_adapters.interfaces;

import com.acelerati.gestionmatricula.domain.model.Estudiante;
import com.acelerati.gestionmatricula.domain.model.Pensum;
import com.acelerati.gestionmatricula.infraestructure.entitys.EstudiantePensumEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;


@Repository
public interface EstudiantePensumRepositoryMySql extends CrudRepository<EstudiantePensumEntity,Long> {
    int countByEstudiante(Estudiante estudiante);
    List<EstudiantePensumEntity> findByEstudianteAndPensum(Estudiante estudiante, Pensum pensum);

    List<EstudiantePensumEntity> findByPensumId(Long PensumId);

    Optional<EstudiantePensumEntity> findByPensumIdAndEstudianteId(Long pensumId, Long estudianteId);
}

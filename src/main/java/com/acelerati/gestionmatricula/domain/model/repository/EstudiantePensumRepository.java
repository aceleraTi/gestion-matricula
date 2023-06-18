package com.acelerati.gestionmatricula.domain.model.repository;

import com.acelerati.gestionmatricula.infraestructure.entitys.EstudiantePensumEntity;

import java.util.Optional;

public interface EstudiantePensumRepository {

    EstudiantePensumEntity registrar(EstudiantePensumEntity estudiantePensumEntity);

    Boolean findByIdPensum(Long idPensum);

    Optional<EstudiantePensumEntity> findByPensumIdAndEstudianteId(Long pensumId, Long estudianteId);

    int countEstudiantePensum(EstudiantePensumEntity estudiantePensum);

}

package com.acelerati.gestionmatricula.domain.model.repository;

import com.acelerati.gestionmatricula.infraestructure.entitys.EstudiantePensumEntity;

public interface EstudiantePensumRepository {

    EstudiantePensumEntity registrar(EstudiantePensumEntity estudiantePensumEntity);

    Boolean findByIdPensum(Long idPensum);

    Boolean findByPensumIdAndEstudianteId(Long pensumId,Long estudianteId);

    int countEstudiantePensum(EstudiantePensumEntity estudiantePensum);

}

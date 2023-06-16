package com.acelerati.gestionmatricula.infraestructure.driven_adapters.interfaces.jpa_repository;

import com.acelerati.gestionmatricula.infraestructure.entitys.EstudiantePensumEntity;

public interface EstudiantePensumRepository {

    EstudiantePensumEntity registrar(EstudiantePensumEntity estudiantePensumEntity);

    Boolean findByIdPensum(Long idPensum);

}

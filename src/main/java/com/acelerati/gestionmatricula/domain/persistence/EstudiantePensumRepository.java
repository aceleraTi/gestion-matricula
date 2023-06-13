package com.acelerati.gestionmatricula.domain.persistence;

import com.acelerati.gestionmatricula.infraestructure.entitys.EstudiantePensumEntity;

public interface EstudiantePensumRepository {

    EstudiantePensumEntity registrar(EstudiantePensumEntity estudiantePensumEntity);

}

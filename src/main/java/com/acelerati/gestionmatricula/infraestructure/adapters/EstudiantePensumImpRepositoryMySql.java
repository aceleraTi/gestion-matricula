package com.acelerati.gestionmatricula.infraestructure.adapters;

import com.acelerati.gestionmatricula.domain.persistence.EstudiantePensumRepository;
import com.acelerati.gestionmatricula.infraestructure.adapters.interfaces.EstudiantePensumRepositoryMySql;

public class EstudiantePensumImpRepositoryMySql implements EstudiantePensumRepository {

    private final EstudiantePensumRepositoryMySql estudiantePensumRepositoryMySql;

    public EstudiantePensumImpRepositoryMySql(EstudiantePensumRepositoryMySql estudiantePensumRepositoryMySql) {
        this.estudiantePensumRepositoryMySql = estudiantePensumRepositoryMySql;
    }
}

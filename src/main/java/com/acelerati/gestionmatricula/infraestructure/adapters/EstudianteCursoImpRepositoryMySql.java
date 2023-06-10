package com.acelerati.gestionmatricula.infraestructure.adapters;

import com.acelerati.gestionmatricula.domain.persistence.EstudianteCursoRepository;
import com.acelerati.gestionmatricula.infraestructure.adapters.interfaces.EstudianteCursoRepositoryMysql;

public class EstudianteCursoImpRepositoryMySql implements EstudianteCursoRepository {

    private final EstudianteCursoRepositoryMysql estudianteCursoRepositoryMysql;

    public EstudianteCursoImpRepositoryMySql(EstudianteCursoRepositoryMysql estudianteCursoRepositoryMysql) {
        this.estudianteCursoRepositoryMysql = estudianteCursoRepositoryMysql;
    }
}

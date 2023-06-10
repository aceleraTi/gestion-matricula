package com.acelerati.gestionmatricula.infraestructure.adapters;

import com.acelerati.gestionmatricula.domain.persistence.EstudianteCursoTareaRepository;
import com.acelerati.gestionmatricula.infraestructure.adapters.interfaces.EstudianteCursoTareaRepositoryMySql;

public class EstudianteCursoTareaImpRepositoryMysql implements EstudianteCursoTareaRepository {
    private final EstudianteCursoTareaRepositoryMySql estudianteCursoTareaRepositoryMySql;

    public EstudianteCursoTareaImpRepositoryMysql(EstudianteCursoTareaRepositoryMySql estudianteCursoTareaRepositoryMySql) {
        this.estudianteCursoTareaRepositoryMySql = estudianteCursoTareaRepositoryMySql;
    }
}

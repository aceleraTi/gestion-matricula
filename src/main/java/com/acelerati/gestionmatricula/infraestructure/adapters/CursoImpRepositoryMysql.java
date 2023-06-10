package com.acelerati.gestionmatricula.infraestructure.adapters;

import com.acelerati.gestionmatricula.domain.persistence.CursoRepository;
import com.acelerati.gestionmatricula.infraestructure.adapters.interfaces.CursoRepositoryMySql;

public class CursoImpRepositoryMysql implements CursoRepository {

    private final CursoRepositoryMySql cursoRepositoryMySql;


    public CursoImpRepositoryMysql(CursoRepositoryMySql cursoRepositoryMySql) {
        this.cursoRepositoryMySql = cursoRepositoryMySql;
    }
}

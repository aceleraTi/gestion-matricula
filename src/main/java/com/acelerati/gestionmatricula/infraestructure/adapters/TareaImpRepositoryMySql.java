package com.acelerati.gestionmatricula.infraestructure.adapters;

import com.acelerati.gestionmatricula.domain.persistence.TareaRepository;
import com.acelerati.gestionmatricula.infraestructure.adapters.interfaces.TareaRepositoryMySql;

public class TareaImpRepositoryMySql implements TareaRepository {
    private final TareaRepositoryMySql tareaRepositoryMySql;

    public TareaImpRepositoryMySql(TareaRepositoryMySql tareaRepositoryMySql) {
        this.tareaRepositoryMySql = tareaRepositoryMySql;
    }
}

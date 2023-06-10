package com.acelerati.gestionmatricula.infraestructure.adapters;

import com.acelerati.gestionmatricula.domain.persistence.HorarioRepository;
import com.acelerati.gestionmatricula.infraestructure.adapters.interfaces.HorarioRepositoryMySql;

public class HorarioImplRepositoryMySql implements HorarioRepository {

    private final HorarioRepositoryMySql horarioRepositoryMySql;

    public HorarioImplRepositoryMySql(HorarioRepositoryMySql horarioRepositoryMySql) {
        this.horarioRepositoryMySql = horarioRepositoryMySql;
    }
}

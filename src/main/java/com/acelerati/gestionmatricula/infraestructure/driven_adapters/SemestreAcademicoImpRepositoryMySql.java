package com.acelerati.gestionmatricula.infraestructure.driven_adapters;

import com.acelerati.gestionmatricula.infraestructure.driven_adapters.interfaces.jpa_repository.SemestreAcademicoRepository;
import com.acelerati.gestionmatricula.infraestructure.driven_adapters.interfaces.SemestreAcademicoRepositoryMySql;
import com.acelerati.gestionmatricula.infraestructure.entitys.SemestreAcademicoEntity;

public class SemestreAcademicoImpRepositoryMySql implements SemestreAcademicoRepository {
    private final SemestreAcademicoRepositoryMySql semestreAcademicoRepositoryMySql;

    public SemestreAcademicoImpRepositoryMySql(SemestreAcademicoRepositoryMySql semestreAcademicoRepositoryMySql) {
        this.semestreAcademicoRepositoryMySql = semestreAcademicoRepositoryMySql;
    }

    @Override
    public SemestreAcademicoEntity save(SemestreAcademicoEntity semestreAcademicoEntity) {
        return semestreAcademicoRepositoryMySql.save(semestreAcademicoEntity);
    }
}

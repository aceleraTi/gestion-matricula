package com.acelerati.gestionmatricula.infraestructure.driven_adapters;

import com.acelerati.gestionmatricula.domain.model.repository.SemestreAcademicoRepository;
import com.acelerati.gestionmatricula.infraestructure.driven_adapters.interfaces.SemestreAcademicoRepositoryMySql;
import com.acelerati.gestionmatricula.infraestructure.entitys.SemestreAcademicoEntity;
import java.util.Optional;

public class SemestreAcademicoImpRepositoryMySql implements SemestreAcademicoRepository {
    private final SemestreAcademicoRepositoryMySql semestreAcademicoRepositoryMySql;

    public SemestreAcademicoImpRepositoryMySql(SemestreAcademicoRepositoryMySql semestreAcademicoRepositoryMySql) {
        this.semestreAcademicoRepositoryMySql = semestreAcademicoRepositoryMySql;
    }

    @Override
    public SemestreAcademicoEntity save(SemestreAcademicoEntity semestreAcademicoEntity) {
        return semestreAcademicoRepositoryMySql.save(semestreAcademicoEntity);
    }
    
    @Override
    public Optional<SemestreAcademicoEntity> findById(Long id) {
        return semestreAcademicoRepositoryMySql.findById(id);
    }
}

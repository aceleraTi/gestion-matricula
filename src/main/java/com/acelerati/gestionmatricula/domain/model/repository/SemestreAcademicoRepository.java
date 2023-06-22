package com.acelerati.gestionmatricula.domain.model.repository;

import com.acelerati.gestionmatricula.infraestructure.entitys.SemestreAcademicoEntity;
import java.util.Optional;

public interface SemestreAcademicoRepository {
    SemestreAcademicoEntity save(SemestreAcademicoEntity semestreAcademicoEntity);
    Optional<SemestreAcademicoEntity> findById(Long id);
}

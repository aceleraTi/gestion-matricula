package com.acelerati.gestionmatricula.infraestructure.driven_adapters.persistence.interfaces.jpa_repository;

import com.acelerati.gestionmatricula.infraestructure.entitys.SemestreAcademicoEntity;

public interface SemestreAcademicoRepository {
    SemestreAcademicoEntity save(SemestreAcademicoEntity semestreAcademicoEntity);
}

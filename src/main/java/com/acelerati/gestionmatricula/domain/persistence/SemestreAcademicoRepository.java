package com.acelerati.gestionmatricula.domain.persistence;

import com.acelerati.gestionmatricula.infraestructure.entitys.SemestreAcademicoEntity;

public interface SemestreAcademicoRepository {
    SemestreAcademicoEntity save(SemestreAcademicoEntity semestreAcademicoEntity);
}

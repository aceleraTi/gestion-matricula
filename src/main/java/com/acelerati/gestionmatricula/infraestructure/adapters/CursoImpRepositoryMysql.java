package com.acelerati.gestionmatricula.infraestructure.adapters;

import com.acelerati.gestionmatricula.domain.persistence.CursoRepository;
import com.acelerati.gestionmatricula.infraestructure.adapters.interfaces.CursoRepositoryMySql;
import com.acelerati.gestionmatricula.infraestructure.entitys.CursoEntity;
import com.acelerati.gestionmatricula.infraestructure.entitys.SemestreAcademicoEntity;
import org.springframework.stereotype.Service;


public class CursoImpRepositoryMysql implements CursoRepository {

    private final CursoRepositoryMySql cursoRepositoryMySql;


    public CursoImpRepositoryMysql(CursoRepositoryMySql cursoRepositoryMySql) {
        this.cursoRepositoryMySql = cursoRepositoryMySql;
    }

    @Override
    public CursoEntity save(CursoEntity cursoEntity) {
        cursoEntity.setSemestreAcademicoEntity(SemestreAcademicoEntity.builder().id(1L).build());
        return cursoRepositoryMySql.save(cursoEntity);

    }
}

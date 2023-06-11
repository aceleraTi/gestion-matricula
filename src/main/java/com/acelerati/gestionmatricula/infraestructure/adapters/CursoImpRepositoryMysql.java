package com.acelerati.gestionmatricula.infraestructure.adapters;

import com.acelerati.gestionmatricula.domain.persistence.CursoRepository;
import com.acelerati.gestionmatricula.infraestructure.adapters.interfaces.CursoRepositoryMySql;
import com.acelerati.gestionmatricula.infraestructure.entitys.CursoEntity;
import com.acelerati.gestionmatricula.infraestructure.entitys.SemestreAcademicoEntity;
import com.acelerati.gestionmatricula.infraestructure.exceptions.NotCreatedInException;


public class CursoImpRepositoryMysql implements CursoRepository {

    private final CursoRepositoryMySql cursoRepositoryMySql;


    public CursoImpRepositoryMysql(CursoRepositoryMySql cursoRepositoryMySql) {
        this.cursoRepositoryMySql = cursoRepositoryMySql;
    }

    @Override
    public CursoEntity save(CursoEntity cursoEntity) {
        cursoEntity.setSemestreAcademicoEntity(SemestreAcademicoEntity.builder().id(1L).build());
        if(esGrupoUnicoMateriaSemetre(cursoEntity.getGrupo(),cursoEntity.getIdMateria(),cursoEntity.getSemestreAcademicoEntity())){
            return cursoRepositoryMySql.save(cursoEntity);
        }
        else{
            System.out.println("Este grupo ya fue asignado");

            throw new NotCreatedInException("El grupo para el semestre y materia ya existe");
        }


    }
    @Override
    public boolean esGrupoUnicoMateriaSemetre(Integer grupo, Long idMateria, SemestreAcademicoEntity semestreAcademicoEntity){
        return cursoRepositoryMySql.countByGrupoAndIdMateriaAndSemestreAcademicoEntity
                (grupo,idMateria,semestreAcademicoEntity)==0;


    }
}

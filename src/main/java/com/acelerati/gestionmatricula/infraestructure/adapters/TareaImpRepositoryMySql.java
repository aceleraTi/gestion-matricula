package com.acelerati.gestionmatricula.infraestructure.adapters;

import com.acelerati.gestionmatricula.domain.persistence.TareaRepository;
import com.acelerati.gestionmatricula.infraestructure.adapters.interfaces.CursoRepositoryMySql;
import com.acelerati.gestionmatricula.infraestructure.adapters.interfaces.TareaRepositoryMySql;
import com.acelerati.gestionmatricula.infraestructure.entitys.TareaEntity;
import com.acelerati.gestionmatricula.infraestructure.exceptions.NotCreatedInException;

public class TareaImpRepositoryMySql implements TareaRepository {
    private final TareaRepositoryMySql tareaRepositoryMySql;
    private final CursoRepositoryMySql cursoRepositoryMySql;

    public TareaImpRepositoryMySql(TareaRepositoryMySql tareaRepositoryMySql, CursoRepositoryMySql cursoRepositoryMySql) {
        this.tareaRepositoryMySql = tareaRepositoryMySql;
        this.cursoRepositoryMySql = cursoRepositoryMySql;
    }

    @Override
    public TareaEntity crearTarea(TareaEntity tareaEntity) {

        if(cursoRepositoryMySql.findByIdAndProfesorAndEstado(tareaEntity.getCurso().getId(),
                tareaEntity.getCurso().getProfesor(),"En Curso")!=null) {
            if (countTareaCurso(tareaEntity)) {
                return tareaRepositoryMySql.save(tareaEntity);
            }
            throw new NotCreatedInException("El curso ya tiene las 3 tareas permitidas");
        }
        throw new NotCreatedInException("El curso no esta habilitado para que usted le asigne tareas");
    }

    private boolean countTareaCurso(TareaEntity tareaEntity){
        return tareaRepositoryMySql.countByCurso(tareaEntity.getCurso())<3;
    }

}

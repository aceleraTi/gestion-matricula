package com.acelerati.gestionmatricula.infraestructure.driven_adapters;

import com.acelerati.gestionmatricula.domain.model.repository.HorarioRepository;
import com.acelerati.gestionmatricula.infraestructure.driven_adapters.interfaces.HorarioRepositoryMySql;
import com.acelerati.gestionmatricula.infraestructure.entitys.CursoEntity;
import com.acelerati.gestionmatricula.infraestructure.entitys.HorarioEntity;
import com.acelerati.gestionmatricula.infraestructure.exceptions.NotCreatedInException;

import java.util.List;

public class HorarioImpRepositoryMySql implements HorarioRepository {

    private final HorarioRepositoryMySql horarioRepositoryMySql;

    public HorarioImpRepositoryMySql(HorarioRepositoryMySql horarioRepositoryMySql) {
        this.horarioRepositoryMySql = horarioRepositoryMySql;
    }

    public HorarioEntity findById(Long id){

        return horarioRepositoryMySql.findById(id).orElse(null);
    }

    @Override
    public List <HorarioEntity> findByCursoEntity(CursoEntity cursoEntity) {
        return horarioRepositoryMySql.findByCurso(cursoEntity);
    }

    @Override
    public HorarioEntity asignarHorario(HorarioEntity horarioEntity) {
        if(countHorariosCurso(horarioEntity)){
            return horarioRepositoryMySql.save(horarioEntity);
        }
        else{
            throw new NotCreatedInException("Este curso ya cuenta con los 5 horarios permitidos");
        }
    }

    private boolean countHorariosCurso(HorarioEntity horarioEntity){
       return horarioRepositoryMySql.countByCurso(horarioEntity.getCurso())<5;
    }
}

package com.acelerati.gestionmatricula.infraestructure.adapters;

import com.acelerati.gestionmatricula.domain.persistence.HorarioRepository;
import com.acelerati.gestionmatricula.infraestructure.adapters.interfaces.HorarioRepositoryMySql;
import com.acelerati.gestionmatricula.infraestructure.entitys.HorarioEntity;
import com.acelerati.gestionmatricula.infraestructure.entitys.SemestreAcademicoEntity;
import com.acelerati.gestionmatricula.infraestructure.exceptions.NotCreatedInException;

public class HorarioImplRepositoryMySql implements HorarioRepository {

    private final HorarioRepositoryMySql horarioRepositoryMySql;

    public HorarioImplRepositoryMySql(HorarioRepositoryMySql horarioRepositoryMySql) {
        this.horarioRepositoryMySql = horarioRepositoryMySql;
    }

    public HorarioEntity findById(Long id){

        return horarioRepositoryMySql.findById(id).orElse(null);
    }

    @Override
    public HorarioEntity asignarHorario(HorarioEntity horarioEntity) {
        if(countHorariosCurso(horarioEntity)){
            return horarioRepositoryMySql.save(horarioEntity);
        }
        else{
            System.out.println("supera la cantidad de dias a la semana");

            throw new NotCreatedInException("Este curso ya cuenta con los 5 horarios permitidos");
        }
    }

    private boolean countHorariosCurso(HorarioEntity horarioEntity){
       return horarioRepositoryMySql.countByCurso(horarioEntity.getCurso())<5;
    }
}

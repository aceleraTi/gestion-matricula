package com.acelerati.gestionmatricula.domain.persistence;

import com.acelerati.gestionmatricula.infraestructure.entitys.CursoEntity;
import com.acelerati.gestionmatricula.infraestructure.entitys.HorarioEntity;

import java.util.List;

public interface HorarioRepository {

    HorarioEntity asignarHorario(HorarioEntity horarioEntity);
    HorarioEntity findById(Long id);
    List<HorarioEntity> findByCursoEntity(CursoEntity cursoEntity);


}

package com.acelerati.gestionmatricula.domain.persistence;

import com.acelerati.gestionmatricula.infraestructure.entitys.HorarioEntity;

public interface HorarioRepository {

    HorarioEntity asignarHorario(HorarioEntity horarioEntity);
    HorarioEntity findById(Long id);
}

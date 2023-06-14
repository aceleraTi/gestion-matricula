package com.acelerati.gestionmatricula.domain.persistence;

import com.acelerati.gestionmatricula.infraestructure.entitys.TareaEntity;

public interface TareaRepository {

    TareaEntity crearTarea(TareaEntity tareaEntity);

    TareaEntity findByTareaId(Long id);

}

package com.acelerati.gestionmatricula.domain.persistence;

import com.acelerati.gestionmatricula.domain.model.Tarea;
import com.acelerati.gestionmatricula.infraestructure.entitys.TareaEntity;

import java.util.List;

public interface TareaRepository {

    TareaEntity crearTarea(TareaEntity tareaEntity);

    TareaEntity findByTareaId(Long id);

    public List<TareaEntity> findByCursoId(Long id);

}

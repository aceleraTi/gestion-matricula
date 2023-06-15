package com.acelerati.gestionmatricula.infraestructure.driven_adapters.persistence.interfaces.jpa_repository;

import com.acelerati.gestionmatricula.infraestructure.entitys.TareaEntity;

import java.util.List;

public interface TareaRepository {

    TareaEntity crearTarea(TareaEntity tareaEntity);

    TareaEntity findByTareaId(Long id);

    public List<TareaEntity> findByCursoId(Long id);

}

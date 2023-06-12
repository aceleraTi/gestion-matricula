package com.acelerati.gestionmatricula.application.service;

import com.acelerati.gestionmatricula.application.service.interfaces.TareaService;
import com.acelerati.gestionmatricula.domain.model.Tarea;
import com.acelerati.gestionmatricula.domain.persistence.TareaRepository;
import com.acelerati.gestionmatricula.infraestructure.entitys.TareaEntity;
import org.springframework.stereotype.Service;

import static com.acelerati.gestionmatricula.infraestructure.rest.mappers.TareaMapper.alaTarea;
import static com.acelerati.gestionmatricula.infraestructure.rest.mappers.TareaMapper.alaTareaEntity;

@Service
public class TareaServiceDefault implements TareaService {
    private final TareaRepository tareaRepository;

    public TareaServiceDefault(TareaRepository tareaRepository) {
        this.tareaRepository = tareaRepository;
    }

    @Override
    public Tarea crearTarea(Tarea tarea) {

        TareaEntity tareaEntity=alaTareaEntity(tarea);
        return alaTarea(tareaRepository.crearTarea(tareaEntity));
    }
}

package com.acelerati.gestionmatricula.application.service;

import com.acelerati.gestionmatricula.application.service.interfaces.TareaService;
import com.acelerati.gestionmatricula.domain.model.Tarea;
import com.acelerati.gestionmatricula.infraestructure.driven_adapters.interfaces.jpa_repository.TareaRepository;
import com.acelerati.gestionmatricula.infraestructure.entitys.TareaEntity;
import com.acelerati.gestionmatricula.infraestructure.rest.mappers.TareaMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public Tarea findByTareaId(Long id) {
        return alaTarea(tareaRepository.findByTareaId(id));
    }
    @Override
    public List<Tarea> findByCursoId(Long id) {
            return tareaRepository.findByCursoId(id).stream()
                .map(TareaMapper::alaTarea)
                .collect(Collectors.toList());
    }
}

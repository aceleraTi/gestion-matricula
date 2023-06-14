package com.acelerati.gestionmatricula.application.service.interfaces;

import com.acelerati.gestionmatricula.domain.model.Tarea;

import java.util.List;

public interface TareaService {

    Tarea crearTarea(Tarea tarea);

    Tarea findByTareaId(Long id);

    List<Tarea> findByCursoId(Long id);

}

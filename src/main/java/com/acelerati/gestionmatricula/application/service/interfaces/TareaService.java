package com.acelerati.gestionmatricula.application.service.interfaces;

import com.acelerati.gestionmatricula.domain.model.Profesor;
import com.acelerati.gestionmatricula.domain.model.Tarea;

public interface TareaService {

    Tarea crearTarea(Tarea tarea, Profesor profesor);

}

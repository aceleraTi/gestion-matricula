package com.acelerati.gestionmatricula.application.service.interfaces;

import com.acelerati.gestionmatricula.domain.model.Tarea;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface TareaService {

    Tarea crearTarea(Tarea tarea, HttpSession session);

}

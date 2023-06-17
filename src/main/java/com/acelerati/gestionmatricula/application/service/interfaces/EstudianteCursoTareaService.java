package com.acelerati.gestionmatricula.application.service.interfaces;

import com.acelerati.gestionmatricula.domain.model.EstudianteCursoTarea;
import com.acelerati.gestionmatricula.domain.model.Profesor;

public interface EstudianteCursoTareaService {

    EstudianteCursoTarea subirNotaTarea(EstudianteCursoTarea estudianteCursoTarea, Profesor profesor);


}

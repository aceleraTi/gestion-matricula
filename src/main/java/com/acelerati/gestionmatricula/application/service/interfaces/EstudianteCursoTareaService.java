package com.acelerati.gestionmatricula.application.service.interfaces;

import com.acelerati.gestionmatricula.domain.model.EstudianteCursoTarea;

import javax.servlet.http.HttpSession;

public interface EstudianteCursoTareaService {

    EstudianteCursoTarea subirNotaTarea(EstudianteCursoTarea estudianteCursoTarea, HttpSession session);


}

package com.acelerati.gestionmatricula.application.service.interfaces;

import com.acelerati.gestionmatricula.domain.model.EstudianteCursoTarea;
import com.acelerati.gestionmatricula.infraestructure.entitys.EstudianteCursoTareaEntity;

public interface EstudianteCursoTareaService {
    EstudianteCursoTarea subirNota(EstudianteCursoTarea estudianteCursoTarea);
}

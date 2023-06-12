package com.acelerati.gestionmatricula.application.service.interfaces;

import com.acelerati.gestionmatricula.domain.model.EstudianteCurso;
import com.acelerati.gestionmatricula.domain.model.Materia;

public interface EstudianteCursoService {
    EstudianteCurso registrarCurso(EstudianteCurso estudianteCurso, Materia materia);
}

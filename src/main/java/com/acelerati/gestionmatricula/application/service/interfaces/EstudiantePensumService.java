package com.acelerati.gestionmatricula.application.service.interfaces;

import com.acelerati.gestionmatricula.domain.model.Estudiante;
import com.acelerati.gestionmatricula.domain.model.EstudiantePensum;
import com.acelerati.gestionmatricula.domain.model.Materia;

import java.util.List;

public interface EstudiantePensumService {
    EstudiantePensum registrar(EstudiantePensum estudiantePensum);

    List<Materia> materiaList(Long idPensum, Estudiante estudiante);
}

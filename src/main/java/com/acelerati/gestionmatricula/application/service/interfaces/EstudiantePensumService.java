package com.acelerati.gestionmatricula.application.service.interfaces;

import com.acelerati.gestionmatricula.domain.model.EstudiantePensum;

public interface EstudiantePensumService {
    EstudiantePensum registrar(EstudiantePensum estudiantePensum);
    Boolean findByIdPensum(Long idPensum);
}

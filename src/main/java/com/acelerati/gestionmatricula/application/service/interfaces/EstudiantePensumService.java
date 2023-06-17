package com.acelerati.gestionmatricula.application.service.interfaces;

import com.acelerati.gestionmatricula.domain.model.EstudiantePensum;
import com.acelerati.gestionmatricula.domain.model.Materia;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface EstudiantePensumService {
    EstudiantePensum registrar(EstudiantePensum estudiantePensum,HttpSession session);
  //  Boolean findByIdPensum(Long idPensum);
    List<Materia> materiaList(Long idPensum, HttpSession session);
}

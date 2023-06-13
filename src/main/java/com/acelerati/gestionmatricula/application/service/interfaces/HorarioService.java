package com.acelerati.gestionmatricula.application.service.interfaces;

import com.acelerati.gestionmatricula.domain.model.Curso;
import com.acelerati.gestionmatricula.domain.model.Horario;

import java.util.List;

public interface HorarioService {

    Horario asignarHorario(Horario horario);
    List<Horario> findByCurso(Curso curso);
}

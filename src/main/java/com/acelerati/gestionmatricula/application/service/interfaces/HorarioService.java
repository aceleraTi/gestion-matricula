package com.acelerati.gestionmatricula.application.service.interfaces;

import com.acelerati.gestionmatricula.domain.model.Curso;
import com.acelerati.gestionmatricula.domain.model.Horario;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface HorarioService {

    Horario asignarHorario(Horario horario, HttpSession session);
    List<Horario> findByCurso(Curso curso);
}

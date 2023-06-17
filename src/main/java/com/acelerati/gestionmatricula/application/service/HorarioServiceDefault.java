package com.acelerati.gestionmatricula.application.service;

import com.acelerati.gestionmatricula.application.service.interfaces.HorarioService;
import com.acelerati.gestionmatricula.domain.model.Curso;
import com.acelerati.gestionmatricula.domain.model.Horario;
import com.acelerati.gestionmatricula.domain.model.Usuario;
import com.acelerati.gestionmatricula.domain.model.repository.CursoRepository;
import com.acelerati.gestionmatricula.domain.model.repository.HorarioRepository;
import com.acelerati.gestionmatricula.infraestructure.entitys.CursoEntity;
import com.acelerati.gestionmatricula.infraestructure.entitys.HorarioEntity;
import com.acelerati.gestionmatricula.infraestructure.rest.mappers.HorarioMapper;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

import static com.acelerati.gestionmatricula.domain.util.Validaciones.validarLogged;
import static com.acelerati.gestionmatricula.infraestructure.rest.mappers.CursoMapper.alCursoEntity;
import static com.acelerati.gestionmatricula.infraestructure.rest.mappers.HorarioMapper.alHorario;
import static com.acelerati.gestionmatricula.infraestructure.rest.mappers.HorarioMapper.alHorarioEntity;

@Service
public class HorarioServiceDefault implements HorarioService {
    private final HorarioRepository horarioRepository;
    private final CursoRepository cursoRepository;

    public HorarioServiceDefault(HorarioRepository horarioRepository, CursoRepository cursoRepository) {
        this.horarioRepository = horarioRepository;
        this.cursoRepository = cursoRepository;
    }

    @Override
    public Horario asignarHorario(Horario horario, HttpSession session) {
        Usuario usuario=(Usuario) session.getAttribute("usuario");
        validarLogged(2L,usuario);
        CursoEntity cursoEntity=cursoRepository.findById(horario.getCurso().getId());

        HorarioEntity horarioEntity=alHorarioEntity(horario);
        horarioEntity.setCurso(cursoEntity);
        return alHorario(horarioRepository.asignarHorario(horarioEntity));
    }

    @Override
    public List<Horario> findByCurso(Curso curso) {
        CursoEntity cursoEntity=alCursoEntity(curso);
         return horarioRepository.findByCursoEntity(cursoEntity).stream()
                .map(HorarioMapper::alHorario)
                .collect(Collectors.toList());

    }
}

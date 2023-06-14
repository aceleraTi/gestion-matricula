package com.acelerati.gestionmatricula.application.service;

import com.acelerati.gestionmatricula.application.service.interfaces.HorarioService;
import com.acelerati.gestionmatricula.domain.model.Curso;
import com.acelerati.gestionmatricula.domain.model.Horario;
import com.acelerati.gestionmatricula.domain.persistence.HorarioRepository;
import com.acelerati.gestionmatricula.infraestructure.entitys.CursoEntity;
import com.acelerati.gestionmatricula.infraestructure.entitys.HorarioEntity;
import com.acelerati.gestionmatricula.infraestructure.rest.mappers.HorarioMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.acelerati.gestionmatricula.infraestructure.rest.mappers.CursoMapper.alCursoEntity;
import static com.acelerati.gestionmatricula.infraestructure.rest.mappers.HorarioMapper.alHorario;
import static com.acelerati.gestionmatricula.infraestructure.rest.mappers.HorarioMapper.alHorarioEntity;

@Service
public class HorarioServiceDefault implements HorarioService {
    private final HorarioRepository horarioRepository;

    public HorarioServiceDefault(HorarioRepository horarioRepository) {
        this.horarioRepository = horarioRepository;
    }

    @Override
    public Horario asignarHorario(Horario horario) {

        HorarioEntity horarioEntity=alHorarioEntity(horario);
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

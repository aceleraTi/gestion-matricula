package com.acelerati.gestionmatricula.application.service;

import com.acelerati.gestionmatricula.application.service.interfaces.HorarioService;
import com.acelerati.gestionmatricula.domain.model.Horario;
import com.acelerati.gestionmatricula.domain.persistence.HorarioRepository;
import com.acelerati.gestionmatricula.infraestructure.entitys.HorarioEntity;
import org.springframework.stereotype.Service;

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
}

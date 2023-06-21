package com.acelerati.gestionmatricula.application.service;

import com.acelerati.gestionmatricula.application.service.interfaces.SemestreAcademicoService;
import com.acelerati.gestionmatricula.domain.model.SemestreAcademico;
import com.acelerati.gestionmatricula.domain.model.repository.SemestreAcademicoRepository;
import com.acelerati.gestionmatricula.infraestructure.entitys.SemestreAcademicoEntity;
import org.springframework.stereotype.Service;

import static com.acelerati.gestionmatricula.infraestructure.rest.mappers.SemestreAcademicoMapper.alSemestreAcademico;
import static com.acelerati.gestionmatricula.infraestructure.rest.mappers.SemestreAcademicoMapper.alSemestreAcademicoEntity;

@Service
public class SemestreAcademicoServiceImplement implements SemestreAcademicoService {

    private final SemestreAcademicoRepository semestreAcademicoRepository;

    public SemestreAcademicoServiceImplement(SemestreAcademicoRepository semestreAcademicoRepository) {
        this.semestreAcademicoRepository = semestreAcademicoRepository;
    }

    @Override
    public SemestreAcademico create(SemestreAcademico semestreAcademico) {
        SemestreAcademicoEntity semestreAcademicoEntity=alSemestreAcademicoEntity(semestreAcademico);
        return alSemestreAcademico(semestreAcademicoRepository.save(semestreAcademicoEntity));
    }
}

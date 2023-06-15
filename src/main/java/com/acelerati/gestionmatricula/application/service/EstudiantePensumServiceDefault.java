package com.acelerati.gestionmatricula.application.service;

import com.acelerati.gestionmatricula.application.service.interfaces.EstudiantePensumService;
import com.acelerati.gestionmatricula.domain.model.EstudiantePensum;
import com.acelerati.gestionmatricula.infraestructure.driven_adapters.persistence.interfaces.jpa_repository.EstudiantePensumRepository;
import com.acelerati.gestionmatricula.infraestructure.entitys.EstudiantePensumEntity;
import org.springframework.stereotype.Service;

import static com.acelerati.gestionmatricula.infraestructure.rest.mappers.EstudiantePensumMapper.alEstudiantePensum;
import static com.acelerati.gestionmatricula.infraestructure.rest.mappers.EstudiantePensumMapper.alEstudiantePensumEntity;

@Service
public class EstudiantePensumServiceDefault implements EstudiantePensumService {
    private final EstudiantePensumRepository estudiantePensumRepository;

    public EstudiantePensumServiceDefault(EstudiantePensumRepository estudiantePensumRepository) {
        this.estudiantePensumRepository = estudiantePensumRepository;
    }

    @Override
    public EstudiantePensum registrar(EstudiantePensum estudiantePensum) {
        EstudiantePensumEntity estudiantePensumEntity=alEstudiantePensumEntity(estudiantePensum);
        return alEstudiantePensum(estudiantePensumRepository.registrar(estudiantePensumEntity));
    }
}

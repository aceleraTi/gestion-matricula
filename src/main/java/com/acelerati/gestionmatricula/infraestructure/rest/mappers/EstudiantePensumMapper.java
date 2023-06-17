package com.acelerati.gestionmatricula.infraestructure.rest.mappers;

import com.acelerati.gestionmatricula.domain.model.EstudiantePensum;
import com.acelerati.gestionmatricula.infraestructure.entitys.EstudiantePensumEntity;

import java.util.Optional;

public class EstudiantePensumMapper {

    public static EstudiantePensum alEstudiantePensum(EstudiantePensumEntity estudiantePensumEntity){

        return new EstudiantePensum(estudiantePensumEntity.getId(),
                estudiantePensumEntity.getEstudiante(),
                estudiantePensumEntity.getPensum());

    }

    public static EstudiantePensumEntity alEstudiantePensumEntity(EstudiantePensum estudiantePensum){
        return EstudiantePensumEntity.builder()
                .id(estudiantePensum.getId())
                .estudiante(estudiantePensum.getEstudiante())
                .pensum(estudiantePensum.getPensum())
                .build();
    }

}

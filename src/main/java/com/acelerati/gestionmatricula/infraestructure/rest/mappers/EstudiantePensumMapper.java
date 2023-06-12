package com.acelerati.gestionmatricula.infraestructure.rest.mappers;

import com.acelerati.gestionmatricula.domain.model.EstudiantePensum;
import com.acelerati.gestionmatricula.infraestructure.entitys.EstudiantePensumEntity;

public class EstudiantePensumMapper {

    public static EstudiantePensum alEstudiantePensum(EstudiantePensumEntity estudiantePensumEntity){
        return EstudiantePensum.builder()
                .id(estudiantePensumEntity.getId())
                .estudiante(estudiantePensumEntity.getEstudiante())
                .pensum(estudiantePensumEntity.getPensum())
                .build();
    }

    public static EstudiantePensumEntity alEstudiantePensumEntity(EstudiantePensum estudiantePensum){
        return EstudiantePensumEntity.builder()
                .id(estudiantePensum.getId())
                .estudiante(estudiantePensum.getEstudiante())
                .pensum(estudiantePensum.getPensum())
                .build();
    }

}

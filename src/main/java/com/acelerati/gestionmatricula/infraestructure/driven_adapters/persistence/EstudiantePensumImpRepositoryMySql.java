package com.acelerati.gestionmatricula.infraestructure.driven_adapters.persistence;

import com.acelerati.gestionmatricula.infraestructure.driven_adapters.persistence.interfaces.jpa_repository.EstudiantePensumRepository;
import com.acelerati.gestionmatricula.infraestructure.driven_adapters.persistence.interfaces.EstudiantePensumRepositoryMySql;
import com.acelerati.gestionmatricula.infraestructure.entitys.EstudiantePensumEntity;
import com.acelerati.gestionmatricula.infraestructure.exceptions.NotCreatedInException;

public class EstudiantePensumImpRepositoryMySql implements EstudiantePensumRepository {

    private final EstudiantePensumRepositoryMySql estudiantePensumRepositoryMySql;

    public EstudiantePensumImpRepositoryMySql(EstudiantePensumRepositoryMySql estudiantePensumRepositoryMySql) {
        this.estudiantePensumRepositoryMySql = estudiantePensumRepositoryMySql;
    }

    @Override
    public EstudiantePensumEntity registrar(EstudiantePensumEntity estudiantePensumEntity) {
        if(countEstudiantePensum(estudiantePensumEntity)) {
            return estudiantePensumRepositoryMySql.save(estudiantePensumEntity);
        }
        throw new NotCreatedInException("Ya estas matriculado en los dos pensum permitidos por estudiante");
    }

    private boolean countEstudiantePensum(EstudiantePensumEntity estudiantePensum){

        return estudiantePensumRepositoryMySql.countByEstudiante(estudiantePensum.getEstudiante())<2;

    }
}

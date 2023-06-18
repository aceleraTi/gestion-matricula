package com.acelerati.gestionmatricula.infraestructure.driven_adapters;

import com.acelerati.gestionmatricula.domain.model.repository.EstudiantePensumRepository;
import com.acelerati.gestionmatricula.infraestructure.driven_adapters.interfaces.EstudiantePensumRepositoryMySql;
import com.acelerati.gestionmatricula.infraestructure.entitys.EstudiantePensumEntity;
import com.acelerati.gestionmatricula.infraestructure.exceptions.NotCreatedInException;

import java.util.Optional;

public class EstudiantePensumImpRepositoryMySql implements EstudiantePensumRepository {

    private final EstudiantePensumRepositoryMySql estudiantePensumRepositoryMySql;

    public EstudiantePensumImpRepositoryMySql(EstudiantePensumRepositoryMySql estudiantePensumRepositoryMySql) {
        this.estudiantePensumRepositoryMySql = estudiantePensumRepositoryMySql;
    }

    @Override
    public EstudiantePensumEntity registrar(EstudiantePensumEntity estudiantePensumEntity) {
         return estudiantePensumRepositoryMySql.save(estudiantePensumEntity);
    }

    @Override
    public Boolean findByIdPensum(Long idPensum) {
        if(estudiantePensumRepositoryMySql.findByPensumId(idPensum).size()>0){
            return true;
        }
        return false;

    }

    @Override
    public Boolean findByPensumIdAndEstudianteId(Long pensumId, Long estudianteId) {
        Optional<EstudiantePensumEntity>estudiantePensumEntityOptional=estudiantePensumRepositoryMySql
                .findByPensumIdAndEstudianteId(pensumId,estudianteId);
        if (estudiantePensumEntityOptional.isPresent())
        {
            return true;
        }
        return false;
    }

    public int countEstudiantePensum(EstudiantePensumEntity estudiantePensum){
        return estudiantePensumRepositoryMySql.countByEstudiante(estudiantePensum.getEstudiante());
    }
}

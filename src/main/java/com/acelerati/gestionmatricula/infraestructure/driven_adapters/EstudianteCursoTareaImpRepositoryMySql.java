package com.acelerati.gestionmatricula.infraestructure.driven_adapters;

import com.acelerati.gestionmatricula.domain.model.repository.EstudianteCursoTareaRepository;
import com.acelerati.gestionmatricula.infraestructure.driven_adapters.interfaces.EstudianteCursoTareaRepositoryMySql;
import com.acelerati.gestionmatricula.infraestructure.entitys.EstudianteCursoTareaEntity;

import java.util.Optional;

public class EstudianteCursoTareaImpRepositoryMySql implements EstudianteCursoTareaRepository {
    private final EstudianteCursoTareaRepositoryMySql estudianteCursoTareaRepositoryMySql;

    public EstudianteCursoTareaImpRepositoryMySql(EstudianteCursoTareaRepositoryMySql estudianteCursoTareaRepositoryMySql) {
        this.estudianteCursoTareaRepositoryMySql = estudianteCursoTareaRepositoryMySql;
    }

    public EstudianteCursoTareaEntity subirNota(EstudianteCursoTareaEntity estudianteCursoTareaEntity){

        return estudianteCursoTareaRepositoryMySql.save(estudianteCursoTareaEntity);

    }

    @Override
    public Double notaTarea(Long idTarea, Long idEstudianteCurso) {
        Optional<EstudianteCursoTareaEntity> optionalEstudianteCursoTareaEntity=estudianteCursoTareaRepositoryMySql.findByTareaIdAndEstudianteCursoId(idTarea,idEstudianteCurso);
       if(optionalEstudianteCursoTareaEntity.isPresent()){
           return optionalEstudianteCursoTareaEntity.get().getNota();
       }
        return 0.0;
    }
}

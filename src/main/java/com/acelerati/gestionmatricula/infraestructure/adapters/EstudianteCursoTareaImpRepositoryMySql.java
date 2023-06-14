package com.acelerati.gestionmatricula.infraestructure.adapters;

import com.acelerati.gestionmatricula.domain.persistence.EstudianteCursoTareaRepository;
import com.acelerati.gestionmatricula.infraestructure.adapters.interfaces.EstudianteCursoTareaRepositoryMySql;
import com.acelerati.gestionmatricula.infraestructure.entitys.EstudianteCursoTareaEntity;

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
        return estudianteCursoTareaRepositoryMySql.findByIdTareaAndIdEstudianteCursoTarea(idTarea,idEstudianteCurso);
    }
}

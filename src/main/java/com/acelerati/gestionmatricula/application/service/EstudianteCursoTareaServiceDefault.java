package com.acelerati.gestionmatricula.application.service;

import com.acelerati.gestionmatricula.application.service.interfaces.EstudianteCursoTareaService;
import com.acelerati.gestionmatricula.domain.model.EstudianteCursoTarea;
import com.acelerati.gestionmatricula.infraestructure.driven_adapters.interfaces.jpa_repository.EstudianteCursoTareaRepository;
import com.acelerati.gestionmatricula.infraestructure.entitys.EstudianteCursoTareaEntity;
import org.springframework.stereotype.Service;

import static com.acelerati.gestionmatricula.infraestructure.rest.mappers.EstudianteCursoTareaMapper.alEstudianteCursoTarea;
import static com.acelerati.gestionmatricula.infraestructure.rest.mappers.EstudianteCursoTareaMapper.alEstudianteCursoTareaEntity;

@Service
public class EstudianteCursoTareaServiceDefault implements EstudianteCursoTareaService {

    private final EstudianteCursoTareaRepository estudianteCursoTareaRepository;

    public EstudianteCursoTareaServiceDefault(EstudianteCursoTareaRepository estudianteCursoTareaRepository) {
        this.estudianteCursoTareaRepository = estudianteCursoTareaRepository;
    }

    @Override
    public EstudianteCursoTarea subirNota(EstudianteCursoTarea estudianteCursoTarea) {

        EstudianteCursoTareaEntity estudianteCursoTareaEntity=alEstudianteCursoTareaEntity(estudianteCursoTarea);
        return alEstudianteCursoTarea(estudianteCursoTareaRepository.subirNota(estudianteCursoTareaEntity));
    }

    @Override
    public Double notaTarea(Long idTarea, Long idEstudianteCurso) {
        return estudianteCursoTareaRepository.notaTarea(idTarea,idEstudianteCurso);
    }
}

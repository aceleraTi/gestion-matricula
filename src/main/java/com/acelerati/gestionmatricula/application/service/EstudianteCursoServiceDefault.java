package com.acelerati.gestionmatricula.application.service;

import com.acelerati.gestionmatricula.application.service.interfaces.EstudianteCursoService;
import com.acelerati.gestionmatricula.domain.model.EstudianteCurso;
import com.acelerati.gestionmatricula.domain.model.Materia;
import com.acelerati.gestionmatricula.domain.persistence.EstudianteCursoRepository;
import com.acelerati.gestionmatricula.infraestructure.entitys.EstudianteCursoEntity;
import org.springframework.stereotype.Service;

import static com.acelerati.gestionmatricula.infraestructure.rest.mappers.EstudianteCursoMapper.alEstudianteCurso;
import static com.acelerati.gestionmatricula.infraestructure.rest.mappers.EstudianteCursoMapper.alEstudianteCursoEntity;

@Service
public class EstudianteCursoServiceDefault implements EstudianteCursoService {
    private  final EstudianteCursoRepository estudianteCursoRepository;

    public EstudianteCursoServiceDefault(EstudianteCursoRepository estudianteCursoRepository) {
        this.estudianteCursoRepository = estudianteCursoRepository;
    }

    @Override
    public EstudianteCurso registrarCurso(EstudianteCurso estudianteCurso, Materia materia) {
        EstudianteCursoEntity estudianteCursoEntity=alEstudianteCursoEntity(estudianteCurso);
        return alEstudianteCurso(estudianteCursoRepository.registrarCurso(estudianteCursoEntity,materia));
    }
}

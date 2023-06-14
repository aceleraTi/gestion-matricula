package com.acelerati.gestionmatricula.application.service;

import com.acelerati.gestionmatricula.application.service.interfaces.EstudianteCursoService;
import com.acelerati.gestionmatricula.domain.model.Curso;
import com.acelerati.gestionmatricula.domain.model.Estudiante;
import com.acelerati.gestionmatricula.domain.model.EstudianteCurso;
import com.acelerati.gestionmatricula.domain.model.Materia;
import com.acelerati.gestionmatricula.domain.persistence.EstudianteCursoRepository;
import com.acelerati.gestionmatricula.infraestructure.entitys.EstudianteCursoEntity;
import com.acelerati.gestionmatricula.infraestructure.rest.mappers.CursoMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    @Override
    public List<Curso> listarEstudianteCurso(Estudiante estudiante) {

        List<EstudianteCursoEntity>estudianteCursoEntities=estudianteCursoRepository.ListarCursosEstudiante(estudiante);

        return estudianteCursoEntities.stream()
                .map(EstudianteCursoEntity::getCurso)
                .filter(p -> p.getEstado().equalsIgnoreCase("En Curso"))
                .flatMap(curso -> Stream.of(CursoMapper.alCurso(curso)))
                .collect(Collectors.toList());

    }


    @Override
    public EstudianteCurso findByEstudianteCursoId(Long id) {

       return  alEstudianteCurso(estudianteCursoRepository.findByEstudianteCursoEntityId(id));
    }
}

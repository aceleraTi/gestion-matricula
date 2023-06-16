package com.acelerati.gestionmatricula.application.service;

import com.acelerati.gestionmatricula.application.service.interfaces.EstudianteCursoService;
import com.acelerati.gestionmatricula.domain.model.Curso;
import com.acelerati.gestionmatricula.domain.model.Estudiante;
import com.acelerati.gestionmatricula.domain.model.EstudianteCurso;
import com.acelerati.gestionmatricula.domain.model.Materia;
import com.acelerati.gestionmatricula.infraestructure.driven_adapters.interfaces.jpa_repository.EstudianteCursoRepository;
import com.acelerati.gestionmatricula.infraestructure.entitys.EstudianteCursoEntity;
import com.acelerati.gestionmatricula.infraestructure.rest.mappers.CursoMapper;
import com.acelerati.gestionmatricula.infraestructure.rest.mappers.EstudianteCursoMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.acelerati.gestionmatricula.infraestructure.rest.mappers.CursoMapper.alCursoEntity;
import static com.acelerati.gestionmatricula.infraestructure.rest.mappers.EstudianteCursoMapper.alEstudianteCurso;
import static com.acelerati.gestionmatricula.infraestructure.rest.mappers.EstudianteCursoMapper.alEstudianteCursoEntity;

@Service
public class EstudianteCursoServiceDefault implements EstudianteCursoService {
    private  final EstudianteCursoRepository estudianteCursoRepository;

    public EstudianteCursoServiceDefault(EstudianteCursoRepository estudianteCursoRepository) {
        this.estudianteCursoRepository = estudianteCursoRepository;
    }

    @Override
    public EstudianteCurso registrarCurso(EstudianteCurso estudianteCurso) {
        EstudianteCursoEntity estudianteCursoEntity=alEstudianteCursoEntity(estudianteCurso);
        return alEstudianteCurso(estudianteCursoRepository.registrarCurso(estudianteCursoEntity));
    }

    @Override
    public EstudianteCurso actualizarCursoEstudiante(EstudianteCurso estudianteCurso) {
        EstudianteCursoEntity estudianteCursoEntity=alEstudianteCursoEntity(estudianteCurso);
        return alEstudianteCurso(estudianteCursoRepository.actualizarCursoEstudiante(estudianteCursoEntity));
    }

    @Override
    public List<Curso> listarCursos(Estudiante estudiante) {

        List<EstudianteCursoEntity>estudianteCursoEntities=estudianteCursoRepository.ListarCursosEstudiante(estudiante);

        return estudianteCursoEntities.stream()
                .map(EstudianteCursoEntity::getCurso)
                .filter(p -> p.getEstado().equalsIgnoreCase("En Curso"))
                .flatMap(curso -> Stream.of(CursoMapper.alCurso(curso)))
                .collect(Collectors.toList());

    }

    @Override
    public List<EstudianteCurso> listarEstudianteCursos(Estudiante estudiante) {
        List<EstudianteCursoEntity>estudianteCursoEntities=estudianteCursoRepository.ListarCursosEstudiante(estudiante);
        return estudianteCursoEntities.stream()
                .map(EstudianteCursoMapper::alEstudianteCurso)
                .collect(Collectors.toList());
    }


    @Override
    public EstudianteCurso findByEstudianteCursoId(Long id) {

       return  alEstudianteCurso(estudianteCursoRepository.findByEstudianteCursoEntityId(id));
    }

    @Override
    public List<EstudianteCurso> findByCurso(Curso curso) {

        List<EstudianteCursoEntity>estudianteCursoEntities=estudianteCursoRepository.findByCurso(alCursoEntity(curso));
        return estudianteCursoEntities.stream()
                .map(EstudianteCursoMapper::alEstudianteCurso)
                .collect(Collectors.toList());
    }

    @Override
    public List<EstudianteCurso> guardarEstudiantesCursos(List<EstudianteCurso> estudianteCurso) {

        List<EstudianteCursoEntity>estudianteCursoEntities=estudianteCursoRepository.guardarEstudiantesCursos(
                estudianteCurso.stream()
                        .map(EstudianteCursoMapper::alEstudianteCursoEntity)
                        .collect(Collectors.toList()));



        return estudianteCursoEntities.stream()
                .map(EstudianteCursoMapper::alEstudianteCurso)
                .collect(Collectors.toList());
    }
}

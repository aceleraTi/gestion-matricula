package com.acelerati.gestionmatricula.application.service;

import com.acelerati.gestionmatricula.application.service.interfaces.EstudiantePensumService;
import com.acelerati.gestionmatricula.domain.model.Estudiante;
import com.acelerati.gestionmatricula.domain.model.EstudiantePensum;
import com.acelerati.gestionmatricula.domain.model.Materia;
import com.acelerati.gestionmatricula.domain.model.Usuario;
import com.acelerati.gestionmatricula.domain.model.repository.CursoRepository;
import com.acelerati.gestionmatricula.domain.model.repository.EstudianteCursoRepository;
import com.acelerati.gestionmatricula.domain.model.repository.EstudiantePensumRepository;
import com.acelerati.gestionmatricula.infraestructure.entitys.CursoEntity;
import com.acelerati.gestionmatricula.infraestructure.entitys.EstudianteCursoEntity;
import com.acelerati.gestionmatricula.infraestructure.entitys.EstudiantePensumEntity;
import com.acelerati.gestionmatricula.infraestructure.exceptions.NotFoundItemsInException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.acelerati.gestionmatricula.domain.util.Validaciones.validarEstudiante;
import static com.acelerati.gestionmatricula.domain.util.Validaciones.validarLogged;
import static com.acelerati.gestionmatricula.infraestructure.rest.mappers.EstudiantePensumMapper.alEstudiantePensum;
import static com.acelerati.gestionmatricula.infraestructure.rest.mappers.EstudiantePensumMapper.alEstudiantePensumEntity;
import static com.acelerati.gestionmatricula.infraestructure.settings.Url.URL_GESTION_ACADEMICA;

@Service
public class EstudiantePensumServiceDefault implements EstudiantePensumService {

    private final EstudiantePensumRepository estudiantePensumRepository;
    private final CursoRepository cursoRepository;
    private final EstudianteCursoRepository estudianteCursoRepository;
    @Autowired
    private RestTemplate restTemplate;
    public EstudiantePensumServiceDefault(EstudiantePensumRepository estudiantePensumRepository, CursoRepository cursoRepository, EstudianteCursoRepository estudianteCursoRepository) {
        this.estudiantePensumRepository = estudiantePensumRepository;
        this.cursoRepository = cursoRepository;
        this.estudianteCursoRepository = estudianteCursoRepository;
    }

    @Override
    public EstudiantePensum registrar(EstudiantePensum estudiantePensum,HttpSession session) {
        validarEstudiante(validarLogged(4L,(Usuario) session.getAttribute("usuario")));
        return alEstudiantePensum(estudiantePensumRepository.registrar(alEstudiantePensumEntity(estudiantePensum)));
    }


    @Override
    public List<Materia> materiaList(Long idPensum, HttpSession session) {

       Estudiante estudiante= validarEstudiante(validarLogged(4L, (Usuario) session.getAttribute("usuario")));
       validarMatriculacionEnPensum(idPensum, estudiante);

        List<Materia> materiasReturn = new ArrayList<>();
        ResponseEntity<List<Materia>> response = obtenerMateriasPensum(idPensum);
        List<Materia> materias = response.getBody();

        materias.stream()
                .filter(materia -> existeEstudianteEnCursosCerrados(estudiante, materia))
                .forEach(materiasReturn::add);

        return materiasReturn;
    }

    private boolean existeEstudianteEnCursosCerrados(Estudiante estudiante, Materia materia) {
        List<CursoEntity> cursoEntityList = obtenerCursosCerrados(materia);
        return cursoEntityList.stream()
                .map(curso -> obtenerEstudianteCurso(estudiante.getId(), curso.getId()))
                .anyMatch(Objects::nonNull);
    }

    private void validarMatriculacionEnPensum(Long idPensum, Estudiante estudiante) {
        if (!estudiantePensumRepository.findByPensumIdAndEstudianteId(idPensum, estudiante.getId())) {
            throw new NotFoundItemsInException("No está matriculado en este pensum");
        }
    }

    private ResponseEntity<List<Materia>> obtenerMateriasPensum(Long idPensum) {
        try {
            return restTemplate.exchange(
                    URL_GESTION_ACADEMICA + "/materias/pensum/" + idPensum,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Materia>>() {}
            );
        } catch (HttpServerErrorException exception) {
            throw new NotFoundItemsInException("No se pudieron obtener las materias del pensum");
        }
    }

    private List<CursoEntity> obtenerCursosCerrados(Materia materia) {
        return cursoRepository.listCursos(materia)
                .stream()
                .filter(p -> p.getEstado().equalsIgnoreCase("Cerrado"))
                .collect(Collectors.toList());
    }

    private EstudianteCursoEntity obtenerEstudianteCurso(Long idEstudiante, Long idCurso) {
        return estudianteCursoRepository.findByEstudianteIdAndCursoId(idEstudiante, idCurso);
    }



}

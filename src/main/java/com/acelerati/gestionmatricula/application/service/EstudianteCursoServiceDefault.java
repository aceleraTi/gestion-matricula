package com.acelerati.gestionmatricula.application.service;

import com.acelerati.gestionmatricula.application.service.interfaces.EstudianteCursoService;
import com.acelerati.gestionmatricula.domain.model.*;
import com.acelerati.gestionmatricula.domain.model.repository.CursoRepository;
import com.acelerati.gestionmatricula.domain.model.repository.EstudianteCursoRepository;
import com.acelerati.gestionmatricula.domain.model.repository.EstudiantePensumRepository;
import com.acelerati.gestionmatricula.domain.model.repository.HorarioRepository;
import com.acelerati.gestionmatricula.infraestructure.entitys.CursoEntity;
import com.acelerati.gestionmatricula.infraestructure.entitys.EstudianteCursoEntity;
import com.acelerati.gestionmatricula.infraestructure.entitys.HorarioEntity;
import com.acelerati.gestionmatricula.infraestructure.exceptions.NotCreatedInException;
import com.acelerati.gestionmatricula.infraestructure.exceptions.NotFoundItemsInException;
import com.acelerati.gestionmatricula.infraestructure.rest.mappers.EstudianteCursoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.acelerati.gestionmatricula.domain.util.Validaciones.*;
import static com.acelerati.gestionmatricula.infraestructure.rest.mappers.CursoMapper.alCursoEntity;
import static com.acelerati.gestionmatricula.infraestructure.rest.mappers.EstudianteCursoMapper.alEstudianteCurso;
import static com.acelerati.gestionmatricula.infraestructure.settings.Url.URL_GESTION_ACADEMICA;

@Service
public class EstudianteCursoServiceDefault implements EstudianteCursoService {
    private final EstudianteCursoRepository estudianteCursoRepository;
    private final CursoRepository cursoRepository;
    private final EstudiantePensumRepository estudiantePensumRepository;
    private final HorarioRepository horarioRepository;
    @Autowired
    private RestTemplate restTemplate;

    public EstudianteCursoServiceDefault(EstudianteCursoRepository estudianteCursoRepository, CursoRepository cursoRepository, EstudiantePensumRepository estudiantePensumRepository, HorarioRepository horarioRepository) {
        this.estudianteCursoRepository = estudianteCursoRepository;
        this.cursoRepository = cursoRepository;
        this.estudiantePensumRepository = estudiantePensumRepository;
        this.horarioRepository = horarioRepository;
    }

    @Override
    public List<EstudianteCurso> findByCurso(Curso curso) {

        List<EstudianteCursoEntity> estudianteCursoEntities = estudianteCursoRepository.findByCurso(alCursoEntity(curso));
        return estudianteCursoEntities.stream()
                .map(EstudianteCursoMapper::alEstudianteCurso)
                .collect(Collectors.toList());
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public EstudianteCurso registrarseEstudianteCurso(Long idCurso, HttpSession session) {
        Estudiante estudiante = validarEstudiante(validarLogged(4L, (Usuario) session.getAttribute("usuario")));
        CursoEntity cursoEntity = obtenerCursoPorId(idCurso);
        Materia materia = obtenerMateria(cursoEntity.getMateria().getId());
        validarMatriculaEnProgramaAcademico(materia, estudiante);
        validarPrerrequisitosAprobados(cursoEntity, estudiante);
        validarEstadoCurso(cursoEntity);
        EstudianteCursoEntity estudianteCursoEntity = crearEstudianteCurso(estudiante, cursoEntity);
        return alEstudianteCurso(estudianteCursoRepository.registrarCurso(estudianteCursoEntity));
    }
    private CursoEntity obtenerCursoPorId(Long idCurso) {
        Optional<CursoEntity> cursoOptional = Optional.ofNullable(cursoRepository.findById(idCurso));
        if (cursoOptional.isEmpty()) {
            throw new NotFoundItemsInException("El curso no existe");
        }
        return cursoOptional.get();
    }
    private Materia obtenerMateria(Long idMateria) {
        try {
            return restTemplate.getForObject(URL_GESTION_ACADEMICA + "/materias/" + idMateria, Materia.class);
        } catch (HttpServerErrorException exception) {
            throw new NotFoundItemsInException("Materia no encontrada");
        } catch (HttpClientErrorException exception) {
            throw new NotFoundItemsInException("La materia no existe");
        }
    }
    private void validarMatriculaEnProgramaAcademico(Materia materia, Estudiante estudiante) {
        if (Boolean.FALSE.equals(estudiantePensumRepository.findByPensumIdAndEstudianteId(materia.getPensum().getId(),estudiante.getId()))) {
            throw new NotFoundItemsInException("No estás matriculado en el programa académico de esta materia");
        }
    }
    private void validarPrerrequisitosAprobados(CursoEntity cursoEntity, Estudiante estudiante) {
        Materia materia = cursoEntity.getMateria();
        if (materia.getMateriaPrerequisito() != null) {
            List<EstudianteCursoEntity> estudianteCursoEntityList = obtenerCursosAprobadosEstudiante(estudiante);
            List<CursoEntity> listCursoEntityList = cursoRepository.listCursos(materia.getMateriaPrerequisito());
            if (!estudianteCursoEntityList.stream().anyMatch(estudianteCurso ->
                    listCursoEntityList.stream().anyMatch(curso ->
                            curso.getId() == estudianteCurso.getCurso().getId() && estudianteCurso.getNotaFinal() > 3.5))) {
                throw new NotFoundItemsInException("Aún no has aprobado el prerrequisito de esta materia");
            }
        }
    }
    private List<EstudianteCursoEntity> obtenerCursosAprobadosEstudiante(Estudiante estudiante) {
        return estudianteCursoRepository.ListarCursosEstudiante(estudiante)
                .stream()
                .filter(estudianteCursoEntity -> estudianteCursoEntity.getNotaFinal() != null)
                .collect(Collectors.toList());
    }
    private void validarEstadoCurso(CursoEntity cursoEntity) {
        if (!cursoEntity.getEstado().equalsIgnoreCase("En Curso")) {
            throw new NotFoundItemsInException("El curso está cerrado");
        }
    }
    private EstudianteCursoEntity crearEstudianteCurso(Estudiante estudiante, CursoEntity cursoEntity) {
        EstudianteCursoEntity estudianteCursoEntity = new EstudianteCursoEntity();
        estudianteCursoEntity.setEstudiante(estudiante);
        estudianteCursoEntity.setCurso(cursoEntity);
        return estudianteCursoEntity;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public List<String> listaHorario(HttpSession session) {

       Estudiante estudiante = validarEstudiante(validarLogged(4L, (Usuario) session.getAttribute("usuario")));

        return listarHorario(listarCursos(estudiante)).stream()
                .flatMap(List::stream)
                .map(horario -> "Curso " + horario.getCurso().getId() + " - Dia " + horario.getDia() + " - Hora " + horario.getHoraInicio())
                .collect(Collectors.toList());
    }
     private List<CursoEntity> listarCursos(Estudiante estudiante) {
        return estudianteCursoRepository.ListarCursosEstudiante(estudiante)
                .stream().map(EstudianteCursoEntity::getCurso)
                .filter(p -> p.getEstado().equalsIgnoreCase("En Curso"))
                .collect(Collectors.toList());
    }
     private List<List<HorarioEntity>> listarHorario(List<CursoEntity> cursoEntityList) {
        return cursoEntityList.stream()
                .map(horarioRepository::findByCursoEntity)
                .collect(Collectors.toList());
    }
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public EstudianteCurso subirNota(EstudianteCurso estudianteCurso, HttpSession session) {
        Profesor profesor = validarProfesor(validarLogged(3L, (Usuario) session.getAttribute("usuario")));
        EstudianteCursoEntity estudianteCursoEntity = estudianteCursoRepository.findByEstudianteCursoEntityId(estudianteCurso.getId());
        validarPrevio3(estudianteCurso);
        validarAsignacionCurso(profesor, estudianteCursoEntity);
        validarPreviosAsignados(estudianteCurso, estudianteCursoEntity);
        asignarPrevios(estudianteCurso, estudianteCursoEntity);
        return alEstudianteCurso(estudianteCursoRepository.actualizarCursoEstudiante(estudianteCursoEntity));
    }
    private void validarPrevio3(EstudianteCurso estudianteCurso) {
        if (estudianteCurso.getPrevio3() != null) {
            throw new NotCreatedInException("El previo 3 corresponde a la sumatoria de las tareas");
        }
    }
    private void validarAsignacionCurso(Profesor profesor, EstudianteCursoEntity estudianteCursoEntity) {
        if (profesor.getId() != estudianteCursoEntity.getCurso().getProfesor().getId()) {
            throw new NotCreatedInException("Usted no es el profesor de este curso.");
        }
    }
    private void validarPreviosAsignados(EstudianteCurso estudianteCurso, EstudianteCursoEntity estudianteCursoEntity) {
        if ((estudianteCurso.getPrevio1() != null && estudianteCursoEntity.getPrevio1() != null) ||
                (estudianteCurso.getPrevio2() != null && estudianteCursoEntity.getPrevio2() != null) ||
                (estudianteCurso.getPrevio4() != null && estudianteCursoEntity.getPrevio4() != null)) {
            throw new NotCreatedInException("Uno de los previos ya tiene nota asignada");
        }
    }
    private void asignarPrevios(EstudianteCurso estudianteCurso, EstudianteCursoEntity estudianteCursoEntity) {
        if (estudianteCurso.getPrevio1() != null) {
            estudianteCursoEntity.setPrevio1(estudianteCurso.getPrevio1());
        }
        if (estudianteCurso.getPrevio2() != null) {
            estudianteCursoEntity.setPrevio2(estudianteCurso.getPrevio2());
        }
        if (estudianteCurso.getPrevio4() != null) {
            estudianteCursoEntity.setPrevio4(estudianteCurso.getPrevio4());
        }
    }



}

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
public class EstudianteCursoServiceImplement implements EstudianteCursoService {
    private final EstudianteCursoRepository estudianteCursoRepository;
    private final CursoRepository cursoRepository;
    private final EstudiantePensumRepository estudiantePensumRepository;
    private final HorarioRepository horarioRepository;
    @Autowired
    private RestTemplate restTemplate;

    public EstudianteCursoServiceImplement(EstudianteCursoRepository estudianteCursoRepository, CursoRepository cursoRepository, EstudiantePensumRepository estudiantePensumRepository, HorarioRepository horarioRepository) {
        this.estudianteCursoRepository = estudianteCursoRepository;
        this.cursoRepository = cursoRepository;
        this.estudiantePensumRepository = estudiantePensumRepository;
        this.horarioRepository = horarioRepository;
    }

    /**
     * se utiliza para buscar y obtener una lista de objetos EstudianteCurso asociados a un curso específico. Aquí tienes una explicación de su funcionamiento:

     * Recibe un objeto Curso como parámetro.
     * Utiliza el repositorio estudianteCursoRepository para buscar las entidades EstudianteCursoEntity asociadas al curso proporcionado.
     * Obtiene una lista de entidades EstudianteCursoEntity que representan la relación entre estudiantes y el curso.
     * Luego, utiliza el método map() junto con EstudianteCursoMapper para convertir cada EstudianteCursoEntity en un objeto EstudianteCurso.
     * Recolecta los objetos EstudianteCurso en una lista.
     * Finalmente, retorna la lista de objetos EstudianteCurso.
     * @param curso
     * @return
     */
    @Override
    public List<EstudianteCurso> findByCurso(Curso curso) {

        List<EstudianteCursoEntity> estudianteCursoEntities = estudianteCursoRepository.findByCurso(alCursoEntity(curso));
        return estudianteCursoEntities.stream()
                .map(EstudianteCursoMapper::alEstudianteCurso)
                .collect(Collectors.toList());
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Este método se encarga de registrar a un estudiante en un curso.
     * Recibe el ID del curso en el que el estudiante desea registrarse y la sesión HTTP del usuario.
     * Primero, se valida que el usuario que realiza la solicitud esté autenticado
     * y sea un estudiante mediante la función validarLogged(4L, (Usuario) session.getAttribute("usuario"))
     * y validarEstudiante().     *
     * Luego, se obtiene la entidad del curso utilizando el método obtenerCursoPorId(idCurso).     *
     * A continuación, se obtiene la información de la materia del curso mediante el
     * método obtenerMateria(cursoEntity.getMateria().getId()).     *

     * Se verifica si el estudiante está matriculado en el programa académico de la materia
     * mediante validarMatriculaEnProgramaAcademico(materia, estudiante).
     * Se verifica si el estudiante ha aprobado los prerrequisitos del curso
     * mediante validarPrerrequisitosAprobados(cursoEntity, estudiante).
     * Se verifica si el estado del curso es "En Curso" mediante validarEstadoCurso(cursoEntity).
     * Si todas las validaciones son exitosas, se crea una instancia de
     * EstudianteCursoEntity mediante crearEstudianteCurso(estudiante, cursoEntity).

     * Finalmente, se utiliza el repositorio estudianteCursoRepository para registrar el curso del estudiante
     * y se retorna la entidad EstudianteCurso correspondiente mediante alEstudianteCurso().
     * @param idCurso
     * @param session
     * @return
     */
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

    /**
     * Este método recibe el ID de un curso y utiliza el repositorio cursoRepository para obtener la entidad del
     * curso correspondiente. Utiliza Optional para manejar el caso en que el curso no exista y lanza una
     * excepción NotFoundItemsInException en ese caso.
     * @param idCurso
     * @return
     */
    private CursoEntity obtenerCursoPorId(Long idCurso) {
        Optional<CursoEntity> cursoOptional = Optional.ofNullable(cursoRepository.findById(idCurso));
        if (cursoOptional.isEmpty()) {
            throw new NotFoundItemsInException("El curso no existe");
        }
        return cursoOptional.get();
    }

    /**
     *  Este método recibe el ID de una materia y realiza una solicitud HTTP a una URL utilizando restTemplate
     *  al microservicio gestion matricula
     *  para obtener la información de la materia. Maneja las excepciones HttpServerErrorException y HttpClientErrorException
     *  y lanza una excepción NotFoundItemsInException en caso de que la materia no sea encontrada o no exista.
     * @param idMateria
     * @return
     */
    private Materia obtenerMateria(Long idMateria) {
        try {
            return restTemplate.getForObject(URL_GESTION_ACADEMICA + "/materias/" + idMateria, Materia.class);
        } catch (HttpServerErrorException exception) {
            throw new NotFoundItemsInException("Materia no encontrada");
        } catch (HttpClientErrorException exception) {
            throw new NotFoundItemsInException("La materia no existe");
        }
    }

    /**
     * Este método valida si un estudiante está matriculado en el programa académico de una materia específica.
     * Utiliza estudiantePensumRepository para verificar la existencia de una relación entre el pensum y el estudiante.
     * Si la relación no existe, lanza una excepción NotFoundItemsInException.
     * @param materia
     * @param estudiante
     */
    private void validarMatriculaEnProgramaAcademico(Materia materia, Estudiante estudiante) {
        if (Boolean.FALSE.equals(estudiantePensumRepository.findByPensumIdAndEstudianteId(materia.getPensum().getId(),estudiante.getId()))) {
            throw new NotFoundItemsInException("No estás matriculado en el programa académico de esta materia");
        }
    }

    /**
     * Este método verifica si un estudiante ha aprobado los prerrequisitos de un curso.
     * Verifica si la materia del curso tiene un prerrequisito y luego obtiene la lista de cursos aprobados del estudiante
     * utilizando obtenerCursosAprobadosEstudiante(). Compara los cursos aprobados del estudiante con la lista de cursos
     * prerrequisitos y, si no hay coincidencias, lanza una excepción NotFoundItemsInException
     * indicando Aún no has aprobado el prerrequisito de esta materia
     * @param cursoEntity
     * @param estudiante
     */
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

    /**
     * Este método obtiene la lista de cursos aprobados por un estudiante.
     * Utiliza estudianteCursoRepository para obtener la lista de entidades EstudianteCursoEntity asociadas al estudiante
     * y luego filtra las entidades que tienen una nota final distinta de null. Retorna la lista resultante.
     * @param estudiante
     * @return
     */
    private List<EstudianteCursoEntity> obtenerCursosAprobadosEstudiante(Estudiante estudiante) {
        return estudianteCursoRepository.ListarCursosEstudiante(estudiante)
                .stream()
                .filter(estudianteCursoEntity -> estudianteCursoEntity.getNotaFinal() != null)
                .collect(Collectors.toList());
    }

    /**
     * Este método valida si el estado de un curso es "En Curso". Verifica el valor del estado en la entidad del curso y,
     * si no coincide, lanza una excepción NotFoundItemsInException indicando que El curso está cerrado.
     * @param cursoEntity
     */
    private void validarEstadoCurso(CursoEntity cursoEntity) {
        if (!cursoEntity.getEstado().equalsIgnoreCase("En Curso")) {
            throw new NotFoundItemsInException("El curso está cerrado");
        }
    }

    /**
     * Este método crea una entidad EstudianteCursoEntity a partir de un estudiante y un curso.
     * Establece el estudiante y el curso en la entidad y la retorna.
     * @param estudiante
     * @param cursoEntity
     * @return
     */
    private EstudianteCursoEntity crearEstudianteCurso(Estudiante estudiante, CursoEntity cursoEntity) {
        EstudianteCursoEntity estudianteCursoEntity = new EstudianteCursoEntity();
        estudianteCursoEntity.setEstudiante(estudiante);
        estudianteCursoEntity.setCurso(cursoEntity);
        return estudianteCursoEntity;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Este método se encarga de listar el horario de los cursos en los que está inscrito un estudiante.
     * Recibe la sesión HTTP del usuario.

     * Primero, se valida que el usuario que realiza la solicitud esté autenticado y sea un estudiante
     * mediante la función validarLogged(4L, (Usuario) session.getAttribute("usuario")) y validarEstudiante().
     *
     * A continuación, se llama al método listarCursos(estudiante) para obtener la lista de cursos
     * en los que el estudiante está inscrito.
     *
     * Luego, se llama al método listarHorario(cursoEntityList) pasando la lista de cursos obtenida anteriormente.
     * Este método devuelve una lista de listas de entidades HorarioEntity, donde cada lista contiene los horarios
     * de un curso en particular.
     *
     * A través de operaciones de streaming y mapeo, se transforma la lista de listas de horarios
     * en una lista plana de cadenas que representan el horario de cada curso.
     * Se utiliza el formato "Curso {ID} - Dia {dia} - Hora {horaInicio}" para cada horario.
     *
     * Finalmente, se devuelve la lista de horarios en forma de cadenas.
     *
     * Este método proporciona al estudiante una lista legible de los horarios de los cursos en los que está inscrito.
     * @param session
     * @return
     */
    @Override
    public List<String> listaHorario(HttpSession session) {

       Estudiante estudiante = validarEstudiante(validarLogged(4L, (Usuario) session.getAttribute("usuario")));

        return listarHorario(listarCursos(estudiante)).stream()
                .flatMap(List::stream)
                .map(horario -> "Curso " + horario.getCurso().getId() + " - Dia " + horario.getDia() + " - Hora " + horario.getHoraInicio())
                .collect(Collectors.toList());
    }

    /**
     * Este método se encarga de obtener la lista de cursos en los que está inscrito un estudiante.
     *
     * Se utiliza el repositorio estudianteCursoRepository para obtener la lista de entidades
     * EstudianteCursoEntity correspondientes al estudiante.
     *
     * Luego, se realiza un mapeo para obtener la lista de entidades CursoEntity a partir de las entidades
     * EstudianteCursoEntity.
     *
     * Se aplica un filtro para mantener solo los cursos que tienen el estado "En Curso".
     *
     * Finalmente, se devuelve la lista de cursos resultante.
     * @param estudiante
     * @return
     */
     private List<CursoEntity> listarCursos(Estudiante estudiante) {
        return estudianteCursoRepository.ListarCursosEstudiante(estudiante)
                .stream().map(EstudianteCursoEntity::getCurso)
                .filter(p -> p.getEstado().equalsIgnoreCase("En Curso"))
                .collect(Collectors.toList());
    }

    /**
     * Este método se encarga de obtener los horarios de los cursos.
     * Se realiza un mapeo de la lista de entidades CursoEntity para obtener una lista de horarios mediante
     * el método horarioRepository.findByCursoEntity(). Cada elemento de la lista resultante es una lista de
     * entidades HorarioEntity que representa los horarios de un curso en particular.

     * Finalmente, se devuelve la lista de listas de horarios resultante.
     * @param cursoEntityList
     * @return
     */
     private List<List<HorarioEntity>> listarHorario(List<CursoEntity> cursoEntityList) {
        return cursoEntityList.stream()
                .map(horarioRepository::findByCursoEntity)
                .collect(Collectors.toList());
    }
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Este método se encarga de subir la nota de un estudiante en un curso.
     * Recibe el objeto EstudianteCurso con la información de la nota a subir y la sesión HTTP del usuario.

     * Primero, se valida que el usuario que realiza la solicitud esté autenticado y sea un profesor mediante
     * la función validarLogged(3L, (Usuario) session.getAttribute("usuario")) y validarProfesor().
     *
     * Luego, se busca la entidad EstudianteCursoEntity correspondiente al EstudianteCurso proporcionado mediante
     * el método estudianteCursoRepository.findByEstudianteCursoEntityId().
     *
     * Se realiza una validación para asegurarse de que el previo 3 no haya sido asignado aún.
     * Si se encuentra asignado, se lanza una excepción NotCreatedInException.
     *
     * Se valida que el profesor que intenta subir la nota sea el profesor asignado al curso.
     * Si no coincide, se lanza una excepción NotCreatedInException.
     *
     * Se verifica que los previos ya asignados no tengan notas asignadas previamente.
     * Si alguno de los previos ya tiene nota asignada, se lanza una excepción NotCreatedInException.
     * @param estudianteCurso
     * @param session
     * @return
     */
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

    /**
     * Este método se encarga de validar que el previo 3 no haya sido asignado aún. Recibe el objeto EstudianteCurso.
     *
     * Se verifica si el valor del previo 3 en el objeto EstudianteCurso no es nulo. Si no es nulo, se lanza una excepción
     * NotCreatedInException indicando que el previo 3 corresponde a la sumatoria de las tareas.
     * @param estudianteCurso
     */
    private void validarPrevio3(EstudianteCurso estudianteCurso) {
        if (estudianteCurso.getPrevio3() != null) {
            throw new NotCreatedInException("El previo 3 corresponde a la sumatoria de las tareas");
        }
    }

    /**
     * Este método se encarga de validar que el profesor que intenta subir la nota sea el profesor asignado al curso.
     * Recibe el objeto Profesor y la entidad EstudianteCursoEntity.
     * Se compara el ID del profesor con el ID del profesor asignado al curso en la entidad EstudianteCursoEntity.
     * Si no coinciden, se lanza una excepción NotCreatedInException indicando que el profesor no es el profesor de este curso.
     * @param profesor
     * @param estudianteCursoEntity
     */
    private void validarAsignacionCurso(Profesor profesor, EstudianteCursoEntity estudianteCursoEntity) {
        if (profesor.getId() != estudianteCursoEntity.getCurso().getProfesor().getId()) {
            throw new NotCreatedInException("Usted no es el profesor de este curso.");
        }
    }

    /**
     * Este método se encarga de validar que los previos proporcionados no tengan notas asignadas previamente.
     * Recibe el objeto EstudianteCurso y la entidad EstudianteCursoEntity.
     * Se verifica si alguno de los previos proporcionados en el objeto EstudianteCurso y
     * en la entidad EstudianteCursoEntity no es nulo. Si alguno de ellos no es nulo,
     * se lanza una excepción NotCreatedInException indicando que uno de los previos ya tiene nota asignada.
     * @param estudianteCurso
     * @param estudianteCursoEntity
     */
    private void validarPreviosAsignados(EstudianteCurso estudianteCurso, EstudianteCursoEntity estudianteCursoEntity) {
        if ((estudianteCurso.getPrevio1() != null && estudianteCursoEntity.getPrevio1() != null) ||
                (estudianteCurso.getPrevio2() != null && estudianteCursoEntity.getPrevio2() != null) ||
                (estudianteCurso.getPrevio4() != null && estudianteCursoEntity.getPrevio4() != null)) {
            throw new NotCreatedInException("Uno de los previos ya tiene nota asignada");
        }
    }

    /**
     * Este método se encarga de asignar los valores de los previos proporcionados en el objeto EstudianteCurso
     * a la entidad EstudianteCursoEntity. Recibe el objeto EstudianteCurso y la entidad EstudianteCursoEntity.
     *
     * Se verifica si el valor del previo 1 en el objeto EstudianteCurso no es nulo.
     * Si no es nulo, se asigna el valor del previo 1 a la propiedad previo1 de la entidad EstudianteCursoEntity.
     *
     * Se verifica si el valor del previo 2 en el objeto EstudianteCurso no es nulo.
     * Si no es nulo, se asigna el valor del previo 2 a la propiedad previo2 de la entidad EstudianteCursoEntity.
     *
     * Se verifica si el valor del previo 4 en el objeto EstudianteCurso no es nulo.
     * Si no es nulo, se asigna el valor del previo 4 a la propiedad previo4 de la entidad EstudianteCursoEntity.
     * @param estudianteCurso
     * @param estudianteCursoEntity
     */
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

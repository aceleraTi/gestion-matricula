package com.acelerati.gestionmatricula.application.service;

import com.acelerati.gestionmatricula.application.service.interfaces.CursoService;
import com.acelerati.gestionmatricula.domain.model.Curso;
import com.acelerati.gestionmatricula.domain.model.Profesor;
import com.acelerati.gestionmatricula.domain.model.Usuario;
import com.acelerati.gestionmatricula.domain.model.repository.CursoRepository;
import com.acelerati.gestionmatricula.domain.model.repository.EstudianteCursoRepository;
import com.acelerati.gestionmatricula.domain.model.repository.EstudianteCursoTareaRepository;
import com.acelerati.gestionmatricula.domain.model.repository.TareaRepository;
import com.acelerati.gestionmatricula.infraestructure.entitys.CursoEntity;
import com.acelerati.gestionmatricula.infraestructure.entitys.EstudianteCursoEntity;
import com.acelerati.gestionmatricula.infraestructure.exceptions.NotCreatedInException;
import com.acelerati.gestionmatricula.infraestructure.exceptions.NotFoundItemsInException;
import com.acelerati.gestionmatricula.infraestructure.exceptions.NotLoggedInException;
import com.acelerati.gestionmatricula.infraestructure.rest.mappers.CursoMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import static com.acelerati.gestionmatricula.infraestructure.rest.mappers.CursoMapper.alCurso;
import static com.acelerati.gestionmatricula.infraestructure.rest.mappers.CursoMapper.alCursoEntity;
import static com.acelerati.gestionmatricula.infraestructure.settings.Url.URL_GESTION_USUARIO;

@Service
public class CursoServiceImplement implements CursoService {

    private final CursoRepository cursoRepository;
    private final EstudianteCursoRepository estudianteCursoRepository;
    private final EstudianteCursoTareaRepository estudianteCursoTareaRepository;
    private final TareaRepository tareaRepository;
    private final RestTemplate restTemplate;

    public CursoServiceImplement(CursoRepository cursoRepository, EstudianteCursoRepository estudianteCursoRepository,
                                 EstudianteCursoTareaRepository estudianteCursoTareaRepository, TareaRepository tareaRepository, RestTemplate restTemplate) {
        this.cursoRepository = cursoRepository;
        this.estudianteCursoRepository = estudianteCursoRepository;
        this.estudianteCursoTareaRepository = estudianteCursoTareaRepository;
        this.tareaRepository = tareaRepository;
        this.restTemplate = restTemplate;
    }

    /**
     * Este es el método principal de crear un curso, que se encarga de crear un
     * curso.Recibe el objeto Curso y la sesión HTTP como parámetros. En primer
     * lugar, se valida que el usuario haya iniciado sesión con el rol adecuado.
     * Luego, se convierte el objeto Curso en un CursoEntity y se realizan las
     * validaciones necesarias antes de guardar el curso en la base de datos.
     *
     * @param curso Curso
     * @return  Curso
     */
    @Override
    public Curso create(Curso curso) {
        CursoEntity cursoEntity = alCursoEntity(curso);
        validarGrupoUnicoMateriaSemestre(cursoEntity);
        validarCantidadCursosProfesor(cursoEntity);
        return alCurso(cursoRepository.save(cursoEntity));
    }

    /**
     * Este método valida si ya existe un grupo único para la combinación de materia y semestre académico del curso.
     * Si ya existe un curso con el mismo grupo, materia y semestre académico,
     * se lanza una excepción NotCreatedInException.
     *
     * @param cursoEntity CursoEntity
     */
    private void validarGrupoUnicoMateriaSemestre(CursoEntity cursoEntity) {
        if (!cursoRepository.esGrupoUnicoMateriaSemetre(cursoEntity.getGrupo(),
                cursoEntity.getMateria(), cursoEntity.getSemestreAcademicoEntity())) {
            throw new NotCreatedInException("El grupo para el semestre y materia ya existe");
        }
    }

    /**
     * Este método verifica si el profesor ya tiene el número máximo permitido de cursos en curso.
     * Si el profesor ya tiene los 4 cursos permitidos, se lanza una excepción NotCreatedInException.
     *
     * @param cursoEntity CursoEntity
     */
    private void validarCantidadCursosProfesor(CursoEntity cursoEntity) {
        if (!cursoRepository.countProfesorCurso(cursoEntity)) {
            throw new NotCreatedInException("El profesor ya tiene los 4 cursos permitidos en curso");
        }
    }

    //---------------------------------------------------------------------------------------------------------

    /**
     * El metodo asignarProfesor
     * Valida que el usuario esté autenticado y tenga el rol adecuado.
     * Valida que el usuario con el ID proporcionado sea un profesor.
     * Obtiene el curso correspondiente al ID proporcionado.
     * Asigna el profesor al curso.
     * Valida la cantidad de cursos que tiene asignados el profesor.
     * Guarda los cambios en el repositorio del curso y retorna el curso actualizado.
     * @param idCurso Long
     * @param idProfesor Long
     *
     * @return Curso
     */
    @Override
    public Curso asignarProfesor(Long idCurso, Long idProfesor) {
        validarUsuarioEsProfesor(idProfesor);
        CursoEntity cursoEntity = obtenerCursoPorId(idCurso);
        asignarProfesorACurso(idProfesor, cursoEntity);
        validarCantidadCursosProfesor(cursoEntity);
        return alCurso(cursoRepository.update(cursoEntity));
    }

    /**
     * Hace una solicitud al microservicio (gestion de usuarios) para obtener información sobre el usuario con el ID proporcionado.
     * Verifica si el usuario es nulo o si su tipo de usuario no es 3 (que corresponde al rol de profesor).
     * Si alguna de estas condiciones se cumple, lanza una excepción indicando que el usuario no tiene el rol de profesor.
     * @param idProfesor Long
     */

    private void validarUsuarioEsProfesor(Long idProfesor) {
        try {
            Usuario validarUsuario = restTemplate.getForObject(URL_GESTION_USUARIO + "/usuarios/" + idProfesor, Usuario.class);
            if (validarUsuario == null || validarUsuario.getTipoUsuario() != 3) {
                throw new NotLoggedInException("Este usuario no tiene rol de profesor");
            }
        } catch (HttpServerErrorException exception) {
            throw new NotLoggedInException("Error al obtener el usuario");
        }
    }

    /**
     * Obtiene el curso correspondiente al ID proporcionado consultando el repositorio de cursos.
     * Si el curso es nulo, lanza una excepción indicando que el curso no se encontró.
     * @param idCurso Long
     * @return CursoEntity
     */
    private CursoEntity obtenerCursoPorId(Long idCurso) {
        Optional<CursoEntity> cursoEntityOptional = cursoRepository.findById(idCurso);
        if (cursoEntityOptional.isEmpty()) {
            throw new NotLoggedInException("Curso no encontrado");
        }
        return cursoEntityOptional.get();
    }

    /**
     * Crea una instancia de la clase Profesor con el ID proporcionado.
     * Asigna el profesor al curso estableciendo la instancia creada como el profesor del curso.
     * @param idProfesor Long
     * @param cursoEntity CursoEntity
     */
    private void asignarProfesorACurso(Long idProfesor, CursoEntity cursoEntity) {
        Profesor profesor = new Profesor();
        profesor.setId(idProfesor);
        cursoEntity.setProfesor(profesor);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Este es el metodo principal de cerrar un curso Valida que el usuario sea
     * un profesor autenticado.Obtiene el profesor autenticado y el curso
     * correspondiente al ID proporcionado. Valida que el profesor tenga
     * permisos para cerrar el curso. Obtiene la lista de entidades de
     * EstudianteCurso correspondientes al curso. Actualiza las notas de los
     * estudiantes del curso. Guarda las entidades de EstudianteCurso
     * actualizadas en el repositorio. Establece el estado del curso como
     * "Cerrado". Retorna el curso actualizado.
     *
     * @param idCurso Long
     * @param profesor Profesor
     *
     * @return Curso
     */
    @Override
    public Curso cerrarCurso(Long idCurso, Profesor profesor) {

        CursoEntity cursoEntity = cursoRepository.findById(idCurso).orElseThrow();
        validarPermisoCerrarCurso(cursoEntity, profesor);
        List<EstudianteCursoEntity> estudianteCursosCursoEntities = obtenerEstudiantesCursos(cursoEntity);
        actualizarNotasEstudiantes(estudianteCursosCursoEntities, idCurso);
        guardarEstudiantesCursos(estudianteCursosCursoEntities);
        cursoEntity.setEstado("Cerrado");
        return alCurso(cursoRepository.update(cursoEntity));
    }

    /**
     * Recorre la lista de entidades de EstudianteCurso del curso. Valida que
     * las notas previas estén presentes. calcula la nota previa 3 utilizando el
     * método calcularNotaPrevio3(). Calcula la nota final del estudiante
     * utilizando las notas previas y la asigna a estud.setNotaFinal().
     *
     * @param estudianteCursosEntities List<EstudianteCursoEntity>
     * @param idCurso idCurso
     */
    private void actualizarNotasEstudiantes(List<EstudianteCursoEntity> estudianteCursosEntities, Long idCurso) {
        for (EstudianteCursoEntity estud : estudianteCursosEntities) {
            validarNotasPrevias(estud);
            if (estud.getNotaFinal() == null) {
                estud.setPrevio3(calcularNotaPrevio3(idCurso, estud.getId()));
                estud.setNotaFinal(((estud.getPrevio1() + estud.getPrevio2()
                        + estud.getPrevio3()) * 7 / 30) + (estud.getPrevio4() * 0.3));
            }
        }
    }

    /**
     * Valida si el profesor autenticado tiene permisos para cerrar el curso. Si
     * el profesor del curso no coincide con el profesor autenticado, lanza una
     * excepción indicando que no tiene permisos.
     *
     * Obtiene la lista de entidades de EstudianteCurso correspondientes al
     * curso proporcionado.
     *
     * @param cursoEntity CursoEntity
     * @param profesor Profesor
     */
    private void validarPermisoCerrarCurso(CursoEntity cursoEntity, Profesor profesor) {
        if (cursoEntity.getProfesor().getId() != profesor.getId()) {
            throw new NotLoggedInException("No tiene permisos para cerrar el curso");
        }
    }

    /**
     * Obtiene la lista de entidades de EstudianteCurso correspondientes al
     * curso proporcionado.
     *
     * @param cursoEntity CursoEntity
     * @return List<EstudianteCursoEntity>
     */
    private List<EstudianteCursoEntity> obtenerEstudiantesCursos(CursoEntity cursoEntity) {
        return estudianteCursoRepository.findByCurso(cursoEntity);
    }

    /**
     * Valida si las notas previas (previo1, previo2 y previo4) del estudiante
     * están presentes. Si alguna de las notas previas no está presente, lanza
     * una excepción indicando que no se han subido todas las notas previas.
     *
     * @param estud EstudianteCursoEntity
     */
    private void validarNotasPrevias(EstudianteCursoEntity estud) {
        if (estud.getPrevio1() == null
                || estud.getPrevio2() == null
                || estud.getPrevio4() == null) {
            throw new NotCreatedInException("Aun no se han subido todas las notas de los previos");
        }
    }

    /**
     * Calcula la nota previa 3 de un estudiante en base a las tareas del curso.
     * Obtiene las tareas del curso mediante el método findByCursoId(). Para
     * cada tarea, obtiene la nota del estudiante correspondiente utilizando el
     * método notaTarea(). Calcula el promedio de las notas de las tareas y lo
     * retorna.
     *
     * @param idCurso Long
     * @param idEstudiante Long
     * @return
     */
    private Double calcularNotaPrevio3(Long idCurso, Long idEstudiante) {
        return tareaRepository.findByCursoId(idCurso)
                .stream()
                .mapToDouble(tar -> estudianteCursoTareaRepository.notaTarea(tar.getId(), idEstudiante))
                .average()
                .orElse(0.0);
    }

    /**
     *
     * Guarda las entidades de EstudianteCurso en el repositorio y retorna la
     * lista actualizada.
     *
     * @param estudianteCursos List<EstudianteCursoEntity>
     * @return List<EstudianteCursoEntity>
     */
    private List<EstudianteCursoEntity> guardarEstudiantesCursos(List<EstudianteCursoEntity> estudianteCursos) {
        return estudianteCursoRepository.guardarEstudiantesCursos(estudianteCursos);
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Recibe un objeto Profesor y un objeto Pageable que define la paginación.
     * Utiliza el repositorio cursoRepository para buscar los cursos asociados
     * al profesor y paginar los resultados. Obtiene una página de entidades
     * CursoEntity. se verifica si hay cursos asignados de lo contrario se
     * retorna una exepcion Luego, utiliza el método map() junto con el
     * CursoMapper para convertir cada CursoEntity en un objeto Curso. Recolecta
     * los objetos Curso en una lista. Finalmente, crea una nueva página
     * (PageImpl) utilizando la lista de cursos, la información de paginación
     * original y el total de elementos de la página de entidades CursoEntity.
     * Retorna esta nueva página de cursos.
     *
     * @param profesor Profesor
     * @param pageable Pageable
     * @return
     */
    @Override
    public Page<Curso> findByProfesor(Profesor profesor, Pageable pageable) {

        Page<CursoEntity> cursoEntityPage = cursoRepository.findByProfesor(profesor, pageable);
        verificarCurso(cursoEntityPage);
        List<Curso> cursoList = cursoEntityPage.getContent()
                .stream()
                .map(CursoMapper::alCurso)
                .collect(Collectors.toList());
        return new PageImpl<>(cursoList, cursoEntityPage.getPageable(), cursoEntityPage.getTotalElements());
    }

    /**
     * valida si hay cursas asignados al profesor.
     *
     * @param cursoEntityPage
     */
    private void verificarCurso(Page<CursoEntity> cursoEntityPage) {
        if (cursoEntityPage.getSize() == 0) {
            throw new NotFoundItemsInException("No se encontraron cursos asignados");
        }
    }

    /**
     * Recibe un ID de curso como parámetro. Utiliza el repositorio
     * cursoRepository para buscar un curso por su ID. Retorna el curso
     * encontrado convertido en un objeto Curso utilizando el método alaCurso().
     *
     * @param id Long
     * @return Curso
     */
    @Override
    public Curso findById(Long id) {
        return alCurso(cursoRepository.findById(id).orElseThrow());
    }

}
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

    /**
     * Este método se encarga de registrar la asociación entre un estudiante y un pensum.
     * Recibe el objeto EstudiantePensum que contiene la información de la asociación y la sesión HTTP
     * para obtener el estudiante que realiza la acción.
     *
     * Se valida que el usuario actualmente logueado sea un estudiante mediante el método validarLogged y
     * se verifica su rol utilizando el ID correspondiente (4L) para estudiante. Luego se obtiene el objeto
     * Estudiante a partir de la sesión HTTP.
     *
     * Se utiliza el método validarEstudiante para validar al estudiante obtenido anteriormente.
     *
     * Se convierte el objeto EstudiantePensum a la entidad EstudiantePensumEntity utilizando el método
     * alEstudiantePensumEntity.
     *
     * Se utiliza el repositorio estudiantePensumRepository para registrar la asociación de estudiante y pensum,
     * pasando la instancia de EstudiantePensumEntity creada.
     *
     * Finalmente, se convierte el resultado a un objeto EstudiantePensum utilizando el método alEstudiantePensum y
     * se devuelve como resultado.
     * @param estudiantePensum
     * @param session
     * @return
     */
    @Override
    public EstudiantePensum registrar(EstudiantePensum estudiantePensum,HttpSession session) {
        validarEstudiante(validarLogged(4L,(Usuario) session.getAttribute("usuario")));
        return alEstudiantePensum(estudiantePensumRepository.registrar(alEstudiantePensumEntity(estudiantePensum)));
    }

    /**
     * Este método obtiene la lista de materias asociadas a un pensum para un estudiante.
     * Recibe el ID del pensum y la sesión HTTP para obtener el estudiante que realiza la acción.
     *
     * Se valida que el usuario actualmente logueado sea un estudiante mediante el método validarLogged
     * y se verifica su rol utilizando el ID correspondiente (4L) para estudiante. Luego se obtiene el
     * objeto Estudiante a partir de la sesión HTTP.
     *
     * Se utiliza el método validarEstudiante para validar al estudiante obtenido anteriormente.
     *
     * Se utiliza el método validarMatriculacionEnPensum para verificar si el estudiante está matriculado en el
     * pensum proporcionado.
     *
     * Se declara una lista materiasReturn para almacenar las materias encontradas.
     *
     * Se utiliza el método obtenerMateriasPensum para obtener las materias asociadas al pensum mediante una
     * solicitud HTTP a la URL correspondiente.
     *
     * Se filtran las materias para verificar si el estudiante está inscrito en algún curso cerrado asociado
     * a cada materia utilizando el método existeEstudianteEnCursosCerrados. Las materias que cumplan esta condición
     * se agregan a la lista materiasReturn.
     *
     * Se devuelve la lista materiasReturn como resultado.
     * @param idPensum
     * @param session
     * @return
     */

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

    /**
     * Este método verifica si un estudiante está inscrito en algún curso cerrado asociado a una materia específica.
     * Recibe el objeto Estudiante y Materia.
     *
     * Se utiliza el método obtenerCursosCerrados para obtener la lista de cursos cerrados asociados a la
     * materia proporcionada.
     *
     * Se verifica si existe una entidad EstudianteCursoEntity que asocie al estudiante y a cada curso cerrado utilizando
     * el método obtenerEstudianteCurso. Si se encuentra al menos una coincidencia, se devuelve true;
     * de lo contrario, se devuelve false.
     * @param estudiante
     * @param materia
     * @return
     */
    private boolean existeEstudianteEnCursosCerrados(Estudiante estudiante, Materia materia) {
        List<CursoEntity> cursoEntityList = obtenerCursosCerrados(materia);
        return cursoEntityList.stream()
                .map(curso -> obtenerEstudianteCurso(estudiante.getId(), curso.getId()))
                .anyMatch(Objects::nonNull);
    }

    /**
     * Este método valida si un estudiante está matriculado en un pensum específico.
     * Recibe el ID del pensum y el objeto Estudiante.
     *
     * Se utiliza el método estudiantePensumRepository.findByPensumIdAndEstudianteId
     * para verificar si existe una asociación entre el ID del pensum y el ID del estudiante
     * en el repositorio estudiantePensumRepository.
     *
     * Si no se encuentra la asociación, se lanza una excepción de tipo NotFoundItemsInException con el mensaje
     * "No está matriculado en este pensum
     *
     * @param idPensum
     * @param estudiante
     */
    private void validarMatriculacionEnPensum(Long idPensum, Estudiante estudiante) {
        if (!estudiantePensumRepository.findByPensumIdAndEstudianteId(idPensum, estudiante.getId())) {
            throw new NotFoundItemsInException("No está matriculado en este pensum");
        }
    }

    /**
     * Este método realiza una solicitud HTTP para obtener la lista de materias asociadas a un pensum.
     * Recibe el ID del pensum y devuelve una respuesta ResponseEntity<List<Materia>> que contiene la lista de materias.
     *
     * Se utiliza el objeto restTemplate para realizar una solicitud GET a la URL correspondiente al
     * servicio de gestión académica y obtener la lista de materias asociadas al pensum.
     *
     * Se utiliza el método exchange de restTemplate para realizar la solicitud HTTP.
     * Se especifica la URL, el método HTTP GET, se pasa null como cuerpo de la solicitud y se utiliza
     * ParameterizedTypeReference<List<Materia>> para especificar que se espera una lista de objetos Materia
     * en la respuesta.
     *
     * Si la respuesta HTTP es exitosa, se devuelve la lista de materias obtenida.
     * En caso de error, se lanza una excepción de tipo NotFoundItemsInException con el mensaje
     * "No se pudieron obtener las materias del pensum".
     * @param idPensum
     * @return
     */
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

    /**
     * Este método obtiene la lista de cursos cerrados asociados a una materia específica.
     * Recibe el objeto Materia y devuelve una lista de objetos CursoEntity.
     *
     * Se utiliza el repositorio cursoRepository para obtener la lista de cursos asociados
     * a la materia proporcionada utilizando el método listCursos(materia).
     *
     * Se filtran los cursos para obtener solamente aquellos que tienen el estado "Cerrado"
     * mediante el uso del método filter.
     *
     * Se recopilan los cursos filtrados en una lista utilizando collect y se devuelve como resultado.
     * @param materia
     * @return
     */
    private List<CursoEntity> obtenerCursosCerrados(Materia materia) {
        return cursoRepository.listCursos(materia)
                .stream()
                .filter(p -> p.getEstado().equalsIgnoreCase("Cerrado"))
                .collect(Collectors.toList());
    }

    /**
     *  Este método obtiene la entidad EstudianteCursoEntity que asocia a un estudiante y un curso específicos.
     *  Recibe el ID del estudiante y el ID del curso, y devuelve la entidad EstudianteCursoEntity.
     *
     * Se utiliza el repositorio estudianteCursoRepository para buscar la entidad EstudianteCursoEntity utilizando
     * los IDs del estudiante y el curso mediante el método findByEstudianteIdAndCursoId.
     *
     * Si se encuentra una coincidencia, se devuelve la entidad EstudianteCursoEntity.
     * @param idEstudiante
     * @param idCurso
     * @return
     */
    private EstudianteCursoEntity obtenerEstudianteCurso(Long idEstudiante, Long idCurso) {
        return estudianteCursoRepository.findByEstudianteIdAndCursoId(idEstudiante, idCurso);
    }



}

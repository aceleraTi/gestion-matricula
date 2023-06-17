package com.acelerati.gestionmatricula.application.service;

import com.acelerati.gestionmatricula.application.service.interfaces.TareaService;
import com.acelerati.gestionmatricula.domain.model.Profesor;
import com.acelerati.gestionmatricula.domain.model.Tarea;
import com.acelerati.gestionmatricula.domain.model.Usuario;
import com.acelerati.gestionmatricula.domain.model.repository.CursoRepository;
import com.acelerati.gestionmatricula.domain.model.repository.TareaRepository;
import com.acelerati.gestionmatricula.infraestructure.entitys.CursoEntity;
import com.acelerati.gestionmatricula.infraestructure.entitys.TareaEntity;
import com.acelerati.gestionmatricula.infraestructure.exceptions.NotFoundItemsInException;
import com.acelerati.gestionmatricula.infraestructure.exceptions.NotLoggedInException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

import java.util.Optional;

import static com.acelerati.gestionmatricula.domain.util.Validaciones.validarLogged;
import static com.acelerati.gestionmatricula.domain.util.Validaciones.validarProfesor;
import static com.acelerati.gestionmatricula.infraestructure.rest.mappers.TareaMapper.alaTarea;
import static com.acelerati.gestionmatricula.infraestructure.rest.mappers.TareaMapper.alaTareaEntity;

@Service
public class TareaServiceDefault implements TareaService {
    private final TareaRepository tareaRepository;
    private final CursoRepository cursoRepository;

    public TareaServiceDefault(TareaRepository tareaRepository, CursoRepository cursoRepository) {
        this.tareaRepository = tareaRepository;
        this.cursoRepository = cursoRepository;
    }

    /**
     * Este método se encarga de crear una nueva tarea. Recibe un objeto Tarea y la sesión actual (HttpSession).     *
     * En primer lugar, se obtiene el profesor de la sesión mediante session.getAttribute("usuario")
     * y se valida que esté autenticado como un usuario con ID 3, utilizando el método validarLogged(3L,
     * (Usuario) session.getAttribute("usuario")).
     *
     * A continuación, se utiliza el método obtenerCursoPorId(tarea.getCurso().getId()) para obtener
     * la entidad del curso asociado a la tarea. Este método busca y devuelve la entidad del curso correspondiente
     * al ID del curso proporcionado en la tarea.
     *
     * Se realiza una validación de autorización para crear la tarea utilizando
     * el método validarAutorizacionCrearTarea(cursoEntity, profesor). Se verifica
     * si el ID del profesor del curso coincide con el ID del profesor autenticado.
     * Si no coinciden, se lanza una excepción indicando que no está autorizado para crear la tarea.
     *
     * Luego, se crea una nueva entidad TareaEntity a partir del objeto Tarea utilizando el método alaTareaEntity(tarea).
     *
     * Se establece la entidad del curso obtenida en el paso anterior en la entidad de la tarea utilizando tareaEntity.
     * setCurso(cursoEntity).
     *
     * Finalmente, se guarda la tarea creada en el repositorio utilizando tareaRepository.crearTarea(tareaEntity),
     * y se devuelve la tarea creada como resultado tras convertirla a un objeto Tarea utilizando el método alaTarea.
     * @param tarea
     * @param session
     * @return
     */
    @Override
    public Tarea crearTarea(Tarea tarea, HttpSession session) {

        Profesor profesor=validarProfesor(validarLogged(3L,(Usuario) session.getAttribute("usuario")));

        CursoEntity cursoEntity = obtenerCursoPorId(tarea.getCurso().getId());
        validarAutorizacionCrearTarea(cursoEntity, profesor);

        TareaEntity tareaEntity = alaTareaEntity(tarea);
        tareaEntity.setCurso(cursoEntity);

        return alaTarea(tareaRepository.crearTarea(tareaEntity));
    }

    /**
     * Este método busca y devuelve la entidad de un curso dado su ID.
     * Recibe el ID del curso como parámetro y devuelve un objeto CursoEntity.
     * Se utiliza el repositorio cursoRepository y el método findById(idCurso)
     * para buscar el curso correspondiente al ID proporcionado.
     *
     * Se envuelve el resultado en un Optional mediante Optional.ofNullable(cursoRepository.findById(idCurso))
     * para permitir manejar el caso en que el curso no exista.
     *
     * Se verifica si el Optional está vacío utilizando cursoOptional.isEmpty().
     * Si está vacío, se lanza una excepción indicando que el curso no existe.
     *
     * Si el Optional no está vacío, se obtiene el valor del Optional utilizando cursoOptional.get()
     * y se devuelve como resultado.
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
     * Este método valida si un profesor está autorizado para crear una tarea en un curso específico.
     * Recibe la entidad del curso y el objeto Profesor.
     *
     * Se compara el ID del profesor del curso (cursoEntity.getProfesor().getId()) con el ID del profesor autenticado
     * (profesor.getId()).
     * Si los IDs no coinciden, se lanza una excepción indicando que el profesor no está autorizado para crear la tarea.
     * @param cursoEntity
     * @param profesor
     */
    private void validarAutorizacionCrearTarea(CursoEntity cursoEntity, Profesor profesor) {
        if (cursoEntity.getProfesor().getId() != profesor.getId()) {
            throw new NotLoggedInException("No está autorizado para crear esta tarea");
        }
    }

}

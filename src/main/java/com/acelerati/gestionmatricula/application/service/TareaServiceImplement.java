package com.acelerati.gestionmatricula.application.service;

import com.acelerati.gestionmatricula.application.service.interfaces.TareaService;
import com.acelerati.gestionmatricula.domain.model.Profesor;
import com.acelerati.gestionmatricula.domain.model.Tarea;
import com.acelerati.gestionmatricula.domain.model.repository.CursoRepository;
import com.acelerati.gestionmatricula.domain.model.repository.TareaRepository;
import com.acelerati.gestionmatricula.infraestructure.entitys.CursoEntity;
import com.acelerati.gestionmatricula.infraestructure.entitys.TareaEntity;
import com.acelerati.gestionmatricula.infraestructure.exceptions.NotCreatedInException;
import com.acelerati.gestionmatricula.infraestructure.exceptions.NotFoundItemsInException;
import com.acelerati.gestionmatricula.infraestructure.exceptions.NotLoggedInException;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.acelerati.gestionmatricula.infraestructure.rest.mappers.TareaMapper.alaTarea;
import static com.acelerati.gestionmatricula.infraestructure.rest.mappers.TareaMapper.alaTareaEntity;

@Service
public class TareaServiceImplement implements TareaService {
    private final TareaRepository tareaRepository;
    private final CursoRepository cursoRepository;

    public TareaServiceImplement(TareaRepository tareaRepository, CursoRepository cursoRepository) {
        this.tareaRepository = tareaRepository;
        this.cursoRepository = cursoRepository;
    }

    /**
     * Este método se encarga de crear una nueva tarea. Recibe un objeto Tarea y profesor.     *
     *
     * se utiliza el método obtenerCursoPorId(tarea.getCurso().getId()) para obtener
     * la entidad del curso asociado a la tarea. Este método busca y devuelve la entidad del curso correspondiente
     * al ID del curso proporcionado en la tarea.
     *
     * Se realiza una validación de autorización para crear la tarea utilizando
     * el método validarAutorizacionCrearTarea(cursoEntity, profesor). Se verifica
     * si el ID del profesor del curso coincide con el ID del profesor autenticado.
     * Si no coinciden, se lanza una excepción indicando que no está autorizado para crear la tarea.
     * se valida la longitud de caracteres en la descripcion de la tarea.
     * Luego, se crea una nueva entidad TareaEntity a partir del objeto Tarea utilizando el método alaTareaEntity(tarea).
     *
     * Se establece la entidad del curso obtenida en el paso anterior en la entidad de la tarea utilizando tareaEntity.
     * setCurso(cursoEntity).
     *
     * Finalmente, se guarda la tarea creada en el repositorio utilizando tareaRepository.crearTarea(tareaEntity),
     * y se devuelve la tarea creada como resultado tras convertirla a un objeto Tarea utilizando el método alaTarea.
     * @param tarea
     *
     * @return
     */
    @Override
    public Tarea crearTarea(Tarea tarea, Profesor profesor) {

        CursoEntity cursoEntity = obtenerCursoPorId(tarea.getCurso().getId());
        validarAutorizacionCrearTarea(cursoEntity, profesor);
        validarEstadoCurso(cursoEntity, profesor);
        validarCaracteresDescripcion(tarea);
        TareaEntity tareaEntity = alaTareaEntity(tarea);
        tareaEntity.setCurso(cursoEntity);
        validarMaximoTareasCurso(tareaEntity);

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
        Optional<CursoEntity> cursoOptional = cursoRepository.findById(idCurso);
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

    /**
     *
     * Valida el estado del curso
     * @param cursoEntity
     * @param profesor
     */
    private void validarEstadoCurso(CursoEntity cursoEntity, Profesor profesor){
        Optional<CursoEntity> optionalCursoEntity=cursoRepository
                .findByIdAndProfesorAndEstado(cursoEntity.getId(),
                        profesor,"En Curso");
        if(optionalCursoEntity.isEmpty()) {
            throw new NotCreatedInException("El curso no esta habilitado, no esta en estado 'EN CURSO'");
        }
    }

    /**
     * Valida si la descripcion de la tarea es mayor o igual  a 100 y menor o igual a 300 respectivamente
     * @param tarea
     */
    private void validarCaracteresDescripcion(Tarea tarea){
        if(tarea.getDescripcion().length()>300 || tarea.getDescripcion().length()<100){
            throw new NotCreatedInException("La descripcion de la tarea esta por fuera del rango permitido de caracteres");
        }
    }

    /**
     * valida que el curso no tenga mas de las 3 tareas permitidas.
     * @param tareaEntity
     */
    private void validarMaximoTareasCurso(TareaEntity tareaEntity){
        if (tareaRepository.countTareaCurso(tareaEntity)>=3) {
            throw new NotCreatedInException("El curso ya tiene las 3 tareas permitidas");
        }

    }

}

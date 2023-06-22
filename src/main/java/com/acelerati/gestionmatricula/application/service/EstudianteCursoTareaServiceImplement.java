package com.acelerati.gestionmatricula.application.service;

import com.acelerati.gestionmatricula.application.service.interfaces.EstudianteCursoTareaService;
import com.acelerati.gestionmatricula.domain.model.EstudianteCursoTarea;
import com.acelerati.gestionmatricula.domain.model.Profesor;
import com.acelerati.gestionmatricula.domain.model.repository.EstudianteCursoRepository;
import com.acelerati.gestionmatricula.domain.model.repository.EstudianteCursoTareaRepository;
import com.acelerati.gestionmatricula.domain.model.repository.TareaRepository;
import com.acelerati.gestionmatricula.infraestructure.entitys.EstudianteCursoEntity;
import com.acelerati.gestionmatricula.infraestructure.entitys.EstudianteCursoTareaEntity;
import com.acelerati.gestionmatricula.infraestructure.entitys.TareaEntity;
import com.acelerati.gestionmatricula.infraestructure.exceptions.NotCreatedInException;
import com.acelerati.gestionmatricula.infraestructure.exceptions.NotFoundItemsInException;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.acelerati.gestionmatricula.infraestructure.rest.mappers.EstudianteCursoTareaMapper.alEstudianteCursoTarea;

@Service
public class EstudianteCursoTareaServiceImplement implements EstudianteCursoTareaService {

    private final EstudianteCursoTareaRepository estudianteCursoTareaRepository;
    private final TareaRepository tareaRepository;
    private final EstudianteCursoRepository estudianteCursoRepository;
    public EstudianteCursoTareaServiceImplement(EstudianteCursoTareaRepository estudianteCursoTareaRepository,
                       TareaRepository tareaRepository, EstudianteCursoRepository estudianteCursoRepository) {
        this.estudianteCursoTareaRepository = estudianteCursoTareaRepository;
        this.tareaRepository = tareaRepository;
        this.estudianteCursoRepository = estudianteCursoRepository;
    }

    /**
     * Este método se encarga de subir la nota de una tarea para un estudiante en un curso específico.

     * Se utiliza el método obtenerTarea para obtener la entidad TareaEntity asociada a la tarea proporcionada
     * en el objeto EstudianteCursoTarea.
     *
     * Se utiliza el método validarAsignacionCurso para verificar si el profesor asignado a la tarea
     * es el mismo que el profesor obtenido en la sesión.
     *
     * Se utiliza el método obtenerEstudianteCurso para obtener la entidad EstudianteCursoEntity asociada
     * al estudiante y curso proporcionados en el objeto EstudianteCursoTarea.
     *
     * Se utiliza el método crearEstudianteCursoTareaEntity para crear una nueva instancia de
     * EstudianteCursoTareaEntity con la entidad EstudianteCursoEntity, la entidad TareaEntity y la nota proporcionada.
     *
     * Se utiliza el repositorio estudianteCursoTareaRepository para subir la nota de la tarea,
     * pasando la instancia de EstudianteCursoTareaEntity creada.
     *
     * Finalmente, se convierte el resultado a un objeto EstudianteCursoTarea utilizando el método
     * alEstudianteCursoTarea y se devuelve como resultado.
     * @param estudianteCursoTarea
     *
     * @return
     */
    @Override
    public EstudianteCursoTarea subirNotaTarea(EstudianteCursoTarea estudianteCursoTarea, Profesor profesor) {

        TareaEntity tareaEntity = obtenerTarea(estudianteCursoTarea);
        validarRangoNotaTarea(estudianteCursoTarea);
        validarAsignacionCurso(profesor, tareaEntity);
        EstudianteCursoEntity estudianteCursoEntity = obtenerEstudianteCurso(estudianteCursoTarea.getEstudianteCurso().getId());
        validarEstudianteCursoTarea(tareaEntity.getId(),estudianteCursoEntity.getId());
        EstudianteCursoTareaEntity estudianteCursoTareaEntity = crearEstudianteCursoTareaEntity(estudianteCursoEntity, tareaEntity, estudianteCursoTarea.getNota());
        return alEstudianteCursoTarea(estudianteCursoTareaRepository.subirNota(estudianteCursoTareaEntity));
    }

    /**
     * Valida que la nota de esta tarea para este estudiante no se haya subido.
     * @param idTarea
     * @param idEstudianteCurso
     */
    private void validarEstudianteCursoTarea(Long idTarea, Long idEstudianteCurso){
        Optional<EstudianteCursoTareaEntity> optionalEstudianteCursoTareaEntity=
                estudianteCursoTareaRepository.existeEstudianteCursoNotaTarea(idTarea,idEstudianteCurso);
        if(optionalEstudianteCursoTareaEntity.isPresent()){
            throw new NotCreatedInException("Ya subio la nota para esta tarea a este estudiante");
        }
    }

    /**
     *Este método se encarga de obtener la entidad TareaEntity correspondiente a la
     * tarea proporcionada en el objeto EstudianteCursoTarea.
     * Recibe el objeto EstudianteCursoTarea y devuelve la entidad TareaEntity.
     *
     * Se utiliza el método findByTareaId del repositorio tareaRepository para buscar la tarea por su ID.
     * Si no se encuentra la tarea, se lanza una excepción NotFoundItemsInException indicando que la tarea no existe.
     * En caso contrario, se devuelve la entidad TareaEntity encontrada.
     *
     * @param estudianteCursoTarea
     * @return
     */


    private TareaEntity obtenerTarea(EstudianteCursoTarea estudianteCursoTarea) {
        TareaEntity tareaEntity = tareaRepository.findByTareaId(estudianteCursoTarea.getTarea().getId());
        if (tareaEntity == null) {
            throw new NotFoundItemsInException("La tarea no existe");
        }
        return tareaEntity;
    }

    /**
     *
     * Valida que la nota de la tarea este dentro del rango de 0 a 5
     * @param estudianteCursoTarea
     */
    private void validarRangoNotaTarea(EstudianteCursoTarea estudianteCursoTarea){
        if(estudianteCursoTarea.getNota()>5 || estudianteCursoTarea.getNota()<0){
            throw new NotCreatedInException("La nota de la tarea debe estar entre el rango de 0 a 5");
        }
    }

    /**
     * Este método valida si el profesor asignado a la tarea es el mismo que el profesor proporcionado.
     * Recibe el objeto Profesor y la entidad TareaEntity.

     * Se verifica si el ID del profesor no coincide con el ID del profesor asignado al curso al que pertenece la tarea.
     * Si los IDs no coinciden, se lanza una excepción NotCreatedInException indicando que el profesor no es el asignado
     * a ese curso
     * @param profesor
     * @param tareaEntity
     */
    private void validarAsignacionCurso(Profesor profesor, TareaEntity tareaEntity) {
        if (profesor.getId() != tareaEntity.getCurso().getProfesor().getId()) {
            throw new NotCreatedInException("Usted no es el profesor de este curso.");
        }
    }

    /**
     * Este método se encarga de obtener la entidad EstudianteCursoEntity correspondiente al estudiante y
     * curso proporcionados en el objeto idEstudianteCursoEntity.
     *
     * Se utiliza el método findByEstudianteCursoEntityId del repositorio estudianteCursoRepository para
     * buscar la entidad EstudianteCursoEntity por su ID. Si no se encuentra, se lanza una
     * excepción NotFoundItemsInException indicando que el estudiante no está matriculado en ese curso.
     * En caso contrario, se devuelve la entidad EstudianteCursoEntity encontrada.
     * @param idEstudianteCursoEntity
     * @return
     */
    private EstudianteCursoEntity obtenerEstudianteCurso(Long idEstudianteCursoEntity) {
      Optional<EstudianteCursoEntity> estudianteCursoEntityOptional = estudianteCursoRepository.findByEstudianteCursoEntityId
                (idEstudianteCursoEntity);
        if (estudianteCursoEntityOptional.isEmpty()) {
            throw new NotFoundItemsInException("El estudiante no esta matriculado en este curso");
        }
        return estudianteCursoEntityOptional.get();
    }

    /**
     * Este método se encarga de crear una nueva instancia de la entidad EstudianteCursoTareaEntity
     * con los valores proporcionados. Recibe la entidad EstudianteCursoEntity, la entidad TareaEntity y la nota.
     *
     * Se crea una nueva instancia de EstudianteCursoTareaEntity y se le asignan la entidad EstudianteCursoEntity,
     * la entidad TareaEntity y la nota proporcionadas.
     *
     * Finalmente, se devuelve la instancia de EstudianteCursoTareaEntity creada.
     * @param estudianteCursoEntity
     * @param tareaEntity
     * @param nota
     * @return
     */
    private EstudianteCursoTareaEntity crearEstudianteCursoTareaEntity(EstudianteCursoEntity estudianteCursoEntity, TareaEntity tareaEntity, Double nota) {
        EstudianteCursoTareaEntity estudianteCursoTareaEntity = new EstudianteCursoTareaEntity();
        estudianteCursoTareaEntity.setEstudianteCurso(estudianteCursoEntity);
        estudianteCursoTareaEntity.setTarea(tareaEntity);
        estudianteCursoTareaEntity.setNota(nota);
        return estudianteCursoTareaEntity;
    }


}

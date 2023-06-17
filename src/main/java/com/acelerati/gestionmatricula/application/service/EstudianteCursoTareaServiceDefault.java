package com.acelerati.gestionmatricula.application.service;

import com.acelerati.gestionmatricula.application.service.interfaces.EstudianteCursoTareaService;
import com.acelerati.gestionmatricula.domain.model.EstudianteCursoTarea;
import com.acelerati.gestionmatricula.domain.model.Profesor;
import com.acelerati.gestionmatricula.domain.model.Usuario;
import com.acelerati.gestionmatricula.domain.model.repository.EstudianteCursoRepository;
import com.acelerati.gestionmatricula.domain.model.repository.EstudianteCursoTareaRepository;
import com.acelerati.gestionmatricula.domain.model.repository.TareaRepository;
import com.acelerati.gestionmatricula.infraestructure.entitys.EstudianteCursoEntity;
import com.acelerati.gestionmatricula.infraestructure.entitys.EstudianteCursoTareaEntity;
import com.acelerati.gestionmatricula.infraestructure.entitys.TareaEntity;
import com.acelerati.gestionmatricula.infraestructure.exceptions.NotCreatedInException;
import com.acelerati.gestionmatricula.infraestructure.exceptions.NotFoundItemsInException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

import static com.acelerati.gestionmatricula.domain.util.Validaciones.validarLogged;
import static com.acelerati.gestionmatricula.domain.util.Validaciones.validarProfesor;
import static com.acelerati.gestionmatricula.infraestructure.rest.mappers.EstudianteCursoTareaMapper.alEstudianteCursoTarea;

@Service
public class EstudianteCursoTareaServiceDefault implements EstudianteCursoTareaService {

    private final EstudianteCursoTareaRepository estudianteCursoTareaRepository;
    private final TareaRepository tareaRepository;
    private final EstudianteCursoRepository estudianteCursoRepository;
    public EstudianteCursoTareaServiceDefault(EstudianteCursoTareaRepository estudianteCursoTareaRepository, TareaRepository tareaRepository, EstudianteCursoRepository estudianteCursoRepository) {
        this.estudianteCursoTareaRepository = estudianteCursoTareaRepository;
        this.tareaRepository = tareaRepository;
        this.estudianteCursoRepository = estudianteCursoRepository;
    }

    @Override
    public EstudianteCursoTarea subirNotaTarea(EstudianteCursoTarea estudianteCursoTarea, HttpSession session) {
        Profesor profesor = validarProfesor(validarLogged(3L, (Usuario) session.getAttribute("usuario")));
        TareaEntity tareaEntity = obtenerTarea(estudianteCursoTarea);
        validarAsignacionCurso(profesor, tareaEntity);
        EstudianteCursoEntity estudianteCursoEntity = obtenerEstudianteCurso(estudianteCursoTarea);
        EstudianteCursoTareaEntity estudianteCursoTareaEntity = crearEstudianteCursoTareaEntity(estudianteCursoEntity, tareaEntity, estudianteCursoTarea.getNota());
        return alEstudianteCursoTarea(estudianteCursoTareaRepository.subirNota(estudianteCursoTareaEntity));
    }

    private TareaEntity obtenerTarea(EstudianteCursoTarea estudianteCursoTarea) {
        TareaEntity tareaEntity = tareaRepository.findByTareaId(estudianteCursoTarea.getTarea().getId());
        if (tareaEntity == null) {
            throw new NotFoundItemsInException("La tarea no existe");
        }
        return tareaEntity;
    }

    private void validarAsignacionCurso(Profesor profesor, TareaEntity tareaEntity) {
        if (profesor.getId() != tareaEntity.getCurso().getProfesor().getId()) {
            throw new NotCreatedInException("Usted no es el profesor de este curso.");
        }
    }

    private EstudianteCursoEntity obtenerEstudianteCurso(EstudianteCursoTarea estudianteCursoTarea) {
        EstudianteCursoEntity estudianteCursoEntity = estudianteCursoRepository.findByEstudianteCursoEntityId(estudianteCursoTarea.getEstudianteCurso().getId());
        if (estudianteCursoEntity == null) {
            throw new NotFoundItemsInException("El estudiante no esta matriculado en este curso");
        }
        return estudianteCursoEntity;
    }

    private EstudianteCursoTareaEntity crearEstudianteCursoTareaEntity(EstudianteCursoEntity estudianteCursoEntity, TareaEntity tareaEntity, Double nota) {
        EstudianteCursoTareaEntity estudianteCursoTareaEntity = new EstudianteCursoTareaEntity();
        estudianteCursoTareaEntity.setEstudianteCurso(estudianteCursoEntity);
        estudianteCursoTareaEntity.setTarea(tareaEntity);
        estudianteCursoTareaEntity.setNota(nota);
        return estudianteCursoTareaEntity;
    }


}

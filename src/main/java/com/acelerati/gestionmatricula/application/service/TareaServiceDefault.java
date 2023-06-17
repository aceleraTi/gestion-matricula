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

    @Override
    public Tarea crearTarea(Tarea tarea, HttpSession session) {

        Profesor profesor=validarProfesor(validarLogged(3L,(Usuario) session.getAttribute("usuario")));

        CursoEntity cursoEntity = obtenerCursoPorId(tarea.getCurso().getId());
        validarAutorizacionCrearTarea(cursoEntity, profesor);

        TareaEntity tareaEntity = alaTareaEntity(tarea);
        tareaEntity.setCurso(cursoEntity);

        return alaTarea(tareaRepository.crearTarea(tareaEntity));
    }

    private CursoEntity obtenerCursoPorId(Long idCurso) {
        Optional<CursoEntity> cursoOptional = Optional.ofNullable(cursoRepository.findById(idCurso));
        if (cursoOptional.isEmpty()) {
            throw new NotFoundItemsInException("El curso no existe");
        }
        return cursoOptional.get();
    }

    private void validarAutorizacionCrearTarea(CursoEntity cursoEntity, Profesor profesor) {
        if (cursoEntity.getProfesor().getId() != profesor.getId()) {
            throw new NotLoggedInException("No está autorizado para crear esta tarea");
        }
    }

}

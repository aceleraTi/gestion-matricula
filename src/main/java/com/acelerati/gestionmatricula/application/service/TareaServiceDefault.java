package com.acelerati.gestionmatricula.application.service;

import com.acelerati.gestionmatricula.application.service.interfaces.TareaService;
import com.acelerati.gestionmatricula.domain.model.Curso;
import com.acelerati.gestionmatricula.domain.model.Tarea;
import com.acelerati.gestionmatricula.domain.model.Usuario;
import com.acelerati.gestionmatricula.domain.model.repository.CursoRepository;
import com.acelerati.gestionmatricula.domain.model.repository.TareaRepository;
import com.acelerati.gestionmatricula.infraestructure.entitys.CursoEntity;
import com.acelerati.gestionmatricula.infraestructure.entitys.TareaEntity;
import com.acelerati.gestionmatricula.infraestructure.exceptions.NotLoggedInException;
import com.acelerati.gestionmatricula.infraestructure.rest.mappers.TareaMapper;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

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
        Usuario usuario=(Usuario) session.getAttribute("usuario");
        validarProfesor(validarLogged(3L,usuario));

        CursoEntity cursoEntity=cursoRepository.findById(tarea.getCurso().getId());
        if (cursoEntity.getProfesor().getId() != usuario.getUsuarioId()) {
            throw new NotLoggedInException("No esta autorizado para crear esta tarea");
        }
        TareaEntity tareaEntity=alaTareaEntity(tarea);
        tareaEntity.setCurso(cursoEntity);
        return alaTarea(tareaRepository.crearTarea(tareaEntity));
    }

 /*   @Override
    public Tarea findByTareaId(Long id) {
        return alaTarea(tareaRepository.findByTareaId(id));
    }
    @Override
    public List<Tarea> findByCursoId(Long id) {
            return tareaRepository.findByCursoId(id).stream()
                .map(TareaMapper::alaTarea)
                .collect(Collectors.toList());
    }*/
}

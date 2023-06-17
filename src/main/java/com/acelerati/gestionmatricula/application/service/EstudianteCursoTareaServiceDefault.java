package com.acelerati.gestionmatricula.application.service;

import com.acelerati.gestionmatricula.application.service.interfaces.EstudianteCursoTareaService;
import com.acelerati.gestionmatricula.domain.model.*;
import com.acelerati.gestionmatricula.domain.model.repository.EstudianteCursoRepository;
import com.acelerati.gestionmatricula.domain.model.repository.EstudianteCursoTareaRepository;
import com.acelerati.gestionmatricula.domain.model.repository.TareaRepository;
import com.acelerati.gestionmatricula.infraestructure.entitys.EstudianteCursoEntity;
import com.acelerati.gestionmatricula.infraestructure.entitys.EstudianteCursoTareaEntity;
import com.acelerati.gestionmatricula.infraestructure.entitys.TareaEntity;
import com.acelerati.gestionmatricula.infraestructure.exceptions.NotCreatedInException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

import static com.acelerati.gestionmatricula.domain.util.Validaciones.validarLogged;
import static com.acelerati.gestionmatricula.domain.util.Validaciones.validarProfesor;
import static com.acelerati.gestionmatricula.infraestructure.rest.mappers.EstudianteCursoTareaMapper.alEstudianteCursoTarea;
import static com.acelerati.gestionmatricula.infraestructure.rest.mappers.EstudianteCursoTareaMapper.alEstudianteCursoTareaEntity;

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

  /*  @Override
    public EstudianteCursoTarea subirNota(EstudianteCursoTarea estudianteCursoTarea) {

        EstudianteCursoTareaEntity estudianteCursoTareaEntity=alEstudianteCursoTareaEntity(estudianteCursoTarea);
        return alEstudianteCursoTarea(estudianteCursoTareaRepository.subirNota(estudianteCursoTareaEntity));
    }*/

  /*  @Override
    public Double notaTarea(Long idTarea, Long idEstudianteCurso) {
        return estudianteCursoTareaRepository.notaTarea(idTarea,idEstudianteCurso);
    }
*/
    @Override
    public EstudianteCursoTarea subirNotaTarea(EstudianteCursoTarea estudianteCursoTarea, HttpSession session) {

        Profesor profesor= validarProfesor(validarLogged(3L,(Usuario) session.getAttribute("usuario")));

        TareaEntity tareaEntity=tareaRepository.findByTareaId(estudianteCursoTarea.getTarea().getId());

        if(profesor.getId()!=tareaEntity.getCurso().getProfesor().getId()){
            throw new NotCreatedInException("Este Curso no esta asignado a usted");
        }

        EstudianteCursoEntity estudianteCursoEntity=estudianteCursoRepository.findByEstudianteCursoEntityId
                (estudianteCursoTarea.getEstudianteCurso().getId());

        EstudianteCursoTareaEntity estudianteCursoTareaEntity=alEstudianteCursoTareaEntity(estudianteCursoTarea);

        estudianteCursoTareaEntity.setEstudianteCurso(estudianteCursoEntity);
        estudianteCursoTareaEntity.setTarea(tareaEntity);

        return alEstudianteCursoTarea(estudianteCursoTareaRepository.subirNota(estudianteCursoTareaEntity));
    }
}

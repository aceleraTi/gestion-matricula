package com.acelerati.gestionmatricula.infraestructure.rest.controllers;

import com.acelerati.gestionmatricula.application.service.interfaces.EstudianteCursoService;
import com.acelerati.gestionmatricula.application.service.interfaces.EstudianteCursoTareaService;
import com.acelerati.gestionmatricula.application.service.interfaces.TareaService;
import com.acelerati.gestionmatricula.domain.model.*;
import com.acelerati.gestionmatricula.infraestructure.exceptions.NotCreatedInException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import static com.acelerati.gestionmatricula.domain.util.Validaciones.validarLogged;
import static com.acelerati.gestionmatricula.domain.util.Validaciones.validarProfesor;

@RestController
@RequestMapping("/cursoTareas")
public class EstudianteCursoTareaController {

    @Autowired
    private EstudianteCursoTareaService estudianteCursoTareaService;
    @Autowired
    private TareaService tareaService;
    @Autowired
    private EstudianteCursoService estudianteCursoService;


    @PostMapping("/subirNota")
    public ResponseEntity<EstudianteCursoTarea> asignarProfesor(@RequestBody EstudianteCursoTarea estudianteCursoTarea,
                                                 HttpSession session){

        Profesor profesor= validarProfesor(validarLogged(3L,session));

        Tarea tarea=tareaService.findByTareaId(estudianteCursoTarea.getTarea().getId());
        if(profesor.getId()!=tarea.getCurso().getProfesor().getId()){
            throw new NotCreatedInException("Este Curso no esta asignado a usted");
        }
       EstudianteCurso estudianteCurso=estudianteCursoService.findByEstudianteCursoId
                (estudianteCursoTarea.getEstudianteCurso().getId());
        estudianteCursoTarea.setEstudianteCurso(estudianteCurso);
        estudianteCursoTarea.setTarea(tarea);
        EstudianteCursoTarea estudianteCursoTareaNotaOk=estudianteCursoTareaService.subirNota(estudianteCursoTarea);

        return new ResponseEntity<>(estudianteCursoTareaNotaOk, HttpStatus.OK);
    }

}

package com.acelerati.gestionmatricula.infraestructure.rest.controllers;

import com.acelerati.gestionmatricula.application.service.interfaces.CursoService;
import com.acelerati.gestionmatricula.application.service.interfaces.TareaService;
import com.acelerati.gestionmatricula.domain.model.Curso;
import com.acelerati.gestionmatricula.domain.model.Profesor;
import com.acelerati.gestionmatricula.domain.model.Tarea;
import com.acelerati.gestionmatricula.domain.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

import static com.acelerati.gestionmatricula.domain.util.Validaciones.validarLogged;
import static com.acelerati.gestionmatricula.domain.util.Validaciones.validarProfesor;

@RestController
@RequestMapping("/tareas")
public class TareaController {

    @Autowired
    private TareaService tareaService;
    @Autowired
    private CursoService cursoService;

    @PostMapping("crearTarea")
    public ResponseEntity<Tarea> crearTarea(@RequestBody Tarea tarea, HttpSession session){

        validarProfesor(validarLogged("profesor",session));

        Curso curso=cursoService.findById(tarea.getCurso().getId());
        tarea.setCurso(curso);
        Tarea tareaCreada=tareaService.crearTarea(tarea);
        return new ResponseEntity<>(tareaCreada, HttpStatus.CREATED);
    }

}

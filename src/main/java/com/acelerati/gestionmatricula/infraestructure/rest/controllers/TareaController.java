package com.acelerati.gestionmatricula.infraestructure.rest.controllers;

import com.acelerati.gestionmatricula.application.service.interfaces.CursoService;
import com.acelerati.gestionmatricula.application.service.interfaces.TareaService;
import com.acelerati.gestionmatricula.domain.model.Curso;
import com.acelerati.gestionmatricula.domain.model.Profesor;
import com.acelerati.gestionmatricula.domain.model.Tarea;
import com.acelerati.gestionmatricula.domain.model.Usuario;
import com.acelerati.gestionmatricula.infraestructure.exceptions.NotLoggedInException;
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

        Usuario usuario=(Usuario) session.getAttribute("usuario");
        validarProfesor(validarLogged(3L,usuario));

        Curso curso=cursoService.findById(tarea.getCurso().getId());
        if (curso.getProfesor().getId() != usuario.getUsuarioId()) {
         throw new NotLoggedInException("No esta autorizado para crear esta tarea");
        }
        tarea.setCurso(curso);
        Tarea tareaCreada=tareaService.crearTarea(tarea);
        return new ResponseEntity<>(tareaCreada, HttpStatus.CREATED);
    }

}

package com.acelerati.gestionmatricula.infraestructure.rest.controllers;

import com.acelerati.gestionmatricula.application.service.interfaces.EstudianteCursoTareaService;
import com.acelerati.gestionmatricula.domain.model.EstudianteCursoTarea;
import com.acelerati.gestionmatricula.domain.model.Profesor;
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
import static com.acelerati.gestionmatricula.infraestructure.settings.Tipo_Usuarios.PROFESOR;

@RestController
@RequestMapping("/cursoTareas")
public class EstudianteCursoTareaController {

    @Autowired
    private EstudianteCursoTareaService estudianteCursoTareaService;


    @PostMapping("/subirNota")
    public ResponseEntity<EstudianteCursoTarea> subirnNotaTarea(@RequestBody EstudianteCursoTarea estudianteCursoTarea,
                                                HttpSession session){
       Profesor profesor = validarProfesor(validarLogged(PROFESOR, (Usuario) session.getAttribute("usuario")));
       return new ResponseEntity<>(estudianteCursoTareaService.subirNotaTarea
               (estudianteCursoTarea,profesor), HttpStatus.OK);
    }

}

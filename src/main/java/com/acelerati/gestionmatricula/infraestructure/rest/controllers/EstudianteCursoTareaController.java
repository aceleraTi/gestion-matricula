package com.acelerati.gestionmatricula.infraestructure.rest.controllers;

import com.acelerati.gestionmatricula.application.service.interfaces.EstudianteCursoTareaService;
import com.acelerati.gestionmatricula.domain.model.EstudianteCursoTarea;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/cursoTareas")
public class EstudianteCursoTareaController {

    @Autowired
    private EstudianteCursoTareaService estudianteCursoTareaService;


    @PostMapping("/subirNota")
    public ResponseEntity<EstudianteCursoTarea> asignarProfesor(@RequestBody EstudianteCursoTarea estudianteCursoTarea,
                                                HttpSession session){
       return new ResponseEntity<>(estudianteCursoTareaService.subirNotaTarea
               (estudianteCursoTarea, session), HttpStatus.OK);
    }

}

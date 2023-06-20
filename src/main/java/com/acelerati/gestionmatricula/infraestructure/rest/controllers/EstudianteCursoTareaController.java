package com.acelerati.gestionmatricula.infraestructure.rest.controllers;

import com.acelerati.gestionmatricula.application.service.interfaces.EstudianteCursoTareaService;
import com.acelerati.gestionmatricula.domain.model.EstudianteCursoTarea;
import com.acelerati.gestionmatricula.domain.model.Profesor;
import com.acelerati.gestionmatricula.domain.model.Usuario;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
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
@Api(tags = "Gestion de Notas Tareas",description = "Permite a un profesor subir notas de las tareas")
public class EstudianteCursoTareaController {

    @Autowired
    private EstudianteCursoTareaService estudianteCursoTareaService;


    @PostMapping("/subirNota")
    @Operation(summary = "Subir nota de una tarea", description ="Permite a un profesor subir notas de las tareas de un curso")
    public ResponseEntity<EstudianteCursoTarea> subirnNotaTarea(@RequestBody EstudianteCursoTarea estudianteCursoTarea,
                                                HttpSession session){
       Profesor profesor = validarProfesor(validarLogged(PROFESOR, (Usuario) session.getAttribute("usuario")));
       return new ResponseEntity<>(estudianteCursoTareaService.subirNotaTarea
               (estudianteCursoTarea,profesor), HttpStatus.OK);
    }

}

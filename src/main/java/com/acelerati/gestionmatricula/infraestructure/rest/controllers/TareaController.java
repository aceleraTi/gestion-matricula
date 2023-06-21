package com.acelerati.gestionmatricula.infraestructure.rest.controllers;

import com.acelerati.gestionmatricula.application.service.interfaces.TareaService;
import com.acelerati.gestionmatricula.domain.model.Profesor;
import com.acelerati.gestionmatricula.domain.model.Tarea;
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
@RequestMapping("/api/v1/tareas")
@Api(tags = "Gestion de Tareas",description = "Permite a un profesor crear una tarea")
public class TareaController {

    @Autowired
    private TareaService tareaService;


    @PostMapping("crearTarea")
    @Operation(summary = "Crea una tarea", description ="Permite a un profesor crear una tarea para un curso.")
    public ResponseEntity<Tarea> crearTarea(@RequestBody Tarea tarea, HttpSession session){
        Profesor profesor=validarProfesor(validarLogged(PROFESOR,(Usuario) session.getAttribute("usuario")));
        return new ResponseEntity<>(tareaService.crearTarea(tarea,profesor), HttpStatus.CREATED);
    }

}

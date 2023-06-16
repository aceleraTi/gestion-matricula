package com.acelerati.gestionmatricula.infraestructure.rest.controllers;


import com.acelerati.gestionmatricula.application.service.interfaces.EstudiantePensumService;
import com.acelerati.gestionmatricula.domain.model.EstudiantePensum;
import com.acelerati.gestionmatricula.domain.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

import static com.acelerati.gestionmatricula.domain.util.Validaciones.validarEstudiante;
import static com.acelerati.gestionmatricula.domain.util.Validaciones.validarLogged;

@RestController
@RequestMapping("/estudiantesPensum")
public class EstudiantePensumController {
    @Autowired
    private EstudiantePensumService estudiantePensumService;
    @PostMapping("/registrar")
    public ResponseEntity<EstudiantePensum> registrar(@RequestBody EstudiantePensum estudiantePensum, HttpSession session) {
        Usuario usuario=(Usuario) session.getAttribute("usuario");
        validarEstudiante(validarLogged(4L,usuario));

        EstudiantePensum estudiantePensumRegistrado = estudiantePensumService.registrar(estudiantePensum);
        return new ResponseEntity<>(estudiantePensumRegistrado, HttpStatus.OK);
    }
}

package com.acelerati.gestionmatricula.infraestructure.rest.controllers;


import com.acelerati.gestionmatricula.application.service.interfaces.EstudiantePensumService;
import com.acelerati.gestionmatricula.domain.model.Estudiante;
import com.acelerati.gestionmatricula.domain.model.EstudiantePensum;
import com.acelerati.gestionmatricula.domain.model.Materia;
import com.acelerati.gestionmatricula.domain.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import java.util.List;

import static com.acelerati.gestionmatricula.domain.util.Validaciones.validarEstudiante;
import static com.acelerati.gestionmatricula.domain.util.Validaciones.validarLogged;
import static com.acelerati.gestionmatricula.infraestructure.settings.Tipo_Usuarios.ESTUDIANTE;

@RestController
@RequestMapping("/estudiantesPensum")
public class EstudiantePensumController {
    @Autowired
    private EstudiantePensumService estudiantePensumService;
    @PostMapping("/registrar")
    public ResponseEntity<EstudiantePensum> registrar(@RequestBody EstudiantePensum estudiantePensum, HttpSession session) {
        Estudiante estudiante= validarEstudiante(validarLogged(ESTUDIANTE,(Usuario) session.getAttribute("usuario")));
        estudiantePensum.setEstudiante(estudiante);
        return new ResponseEntity<>(estudiantePensumService.registrar(estudiantePensum), HttpStatus.OK);
    }

    @GetMapping("/consultarMaterias/{idPensum}")
    public ResponseEntity<List<Materia>> consultarListaMaterias(@PathVariable("idPensum")Long idPensum,HttpSession session){
        Estudiante estudiante= validarEstudiante(validarLogged(ESTUDIANTE, (Usuario) session.getAttribute("usuario")));

        return new ResponseEntity<>(estudiantePensumService.materiaList(idPensum, estudiante),HttpStatus.OK);
    }
}

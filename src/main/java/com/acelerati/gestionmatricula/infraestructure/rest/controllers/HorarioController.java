package com.acelerati.gestionmatricula.infraestructure.rest.controllers;

import com.acelerati.gestionmatricula.application.service.interfaces.CursoService;
import com.acelerati.gestionmatricula.application.service.interfaces.HorarioService;
import com.acelerati.gestionmatricula.domain.model.Curso;
import com.acelerati.gestionmatricula.domain.model.Horario;

import com.acelerati.gestionmatricula.domain.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import static com.acelerati.gestionmatricula.domain.util.Validaciones.validarLogged;

@RestController
@RequestMapping("/horarios")
public class HorarioController {

    @Autowired
    private HorarioService horarioService;

    @PostMapping("/asignar")
    public ResponseEntity<Horario>asignar(@RequestBody Horario horario, HttpSession session) {

        System.out.println("ddd"+horario.getCurso().getSemestreAcademico().getId());
                Horario horarioAsignado = horarioService.asignarHorario(horario, session);
                return new ResponseEntity<>(horarioAsignado, HttpStatus.OK);
    }




}

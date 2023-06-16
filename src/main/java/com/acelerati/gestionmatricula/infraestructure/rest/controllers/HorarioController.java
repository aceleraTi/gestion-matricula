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
    @Autowired
    private CursoService cursoService;
    @PostMapping("/asignar")
    public ResponseEntity<Horario>asignar(@RequestBody Horario horario, HttpSession session) {
        Usuario usuario=(Usuario) session.getAttribute("usuario");
                validarLogged(2L,usuario);
                Curso curso=cursoService.findById(horario.getCurso().getId());
                horario.setCurso(curso);

                Horario horarioAsignado = horarioService.asignarHorario(horario);
                return new ResponseEntity<>(horarioAsignado, HttpStatus.OK);
    }




}

package com.acelerati.gestionmatricula.infraestructure.rest.controllers;

import com.acelerati.gestionmatricula.application.service.interfaces.HorarioService;
import com.acelerati.gestionmatricula.domain.model.Horario;
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
import static com.acelerati.gestionmatricula.infraestructure.settings.Tipo_Usuarios.DIRECTOR;

@RestController
@RequestMapping("/horarios")
@Api(tags = "Gestion de Horario",description = "Permite a un director crearle un horario a un curso.")
public class HorarioController {

    @Autowired
    private HorarioService horarioService;

    @PostMapping("/asignar")
    @Operation(summary = "Asigna un horario a un curso", description ="Permite a un director asignarle  horarios a un curso")
    public ResponseEntity<Horario>asignar(@RequestBody Horario horario, HttpSession session) {

         validarLogged(DIRECTOR,(Usuario) session.getAttribute("usuario"));
                Horario horarioAsignado = horarioService.asignarHorario(horario);
                return new ResponseEntity<>(horarioAsignado, HttpStatus.OK);
    }




}

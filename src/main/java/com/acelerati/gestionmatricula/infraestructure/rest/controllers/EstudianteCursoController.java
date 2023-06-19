package com.acelerati.gestionmatricula.infraestructure.rest.controllers;

import com.acelerati.gestionmatricula.application.service.interfaces.EstudianteCursoService;
import com.acelerati.gestionmatricula.domain.model.Estudiante;
import com.acelerati.gestionmatricula.domain.model.EstudianteCurso;
import com.acelerati.gestionmatricula.domain.model.Profesor;
import com.acelerati.gestionmatricula.domain.model.Usuario;


import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

import static com.acelerati.gestionmatricula.domain.util.Validaciones.*;
import static com.acelerati.gestionmatricula.infraestructure.settings.Tipo_Usuarios.ESTUDIANTE;
import static com.acelerati.gestionmatricula.infraestructure.settings.Tipo_Usuarios.PROFESOR;

@RestController
@RequestMapping("/estudianteCursos")
@Api(tags = "Gestion de Estudiante en Curso",description = "Permite a un estudiante registrarse en un curso, consultar un horario," +
        "y a un profesor subri notas de previos.")
public class EstudianteCursoController {
    @Autowired
    private EstudianteCursoService estudianteCursoService;

    @PostMapping("/registrar/{idCurso}")
    @Operation(summary = "Registrarse en un curso", description ="Permite a un estudiante registrarse en un curso")
    public ResponseEntity<EstudianteCurso> registrar(@PathVariable("idCurso")Long idCurso, HttpSession session) {
        Estudiante estudiante = validarEstudiante(validarLogged(ESTUDIANTE, (Usuario) session.getAttribute("usuario")));
        return new ResponseEntity<>(estudianteCursoService.registrarseEstudianteCurso(idCurso,estudiante), HttpStatus.OK);
    }

    @GetMapping("/consultarHorarioVigente")
    @Operation(summary = "Consulta un horario", description ="Permite a un estudiante consultar un horario")
    public ResponseEntity<List<String>>consultarHorario(HttpSession session){
        return new ResponseEntity<>(estudianteCursoService.listaHorario(session),HttpStatus.OK);
    }

    @PutMapping("/subirPrevio")
    @Operation(summary = "Subir nota de un previo", description ="Permite a un estudiante subir la nota de un previo")
    public ResponseEntity<EstudianteCurso> subirNota(@RequestBody EstudianteCurso estudianteCurso, HttpSession session){
        Profesor profesor = validarProfesor(validarLogged(PROFESOR, (Usuario) session.getAttribute("usuario")));
         return new ResponseEntity<>(estudianteCursoService.subirNota(estudianteCurso,profesor),HttpStatus.OK);
    }






}

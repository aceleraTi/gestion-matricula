package com.acelerati.gestionmatricula.infraestructure.rest.controllers;

import com.acelerati.gestionmatricula.application.service.interfaces.*;
import com.acelerati.gestionmatricula.domain.model.*;
import com.acelerati.gestionmatricula.infraestructure.exceptions.NotCreatedInException;
import com.acelerati.gestionmatricula.infraestructure.exceptions.NotFoundItemsInException;
import com.acelerati.gestionmatricula.infraestructure.exceptions.NotLoggedInException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

import static com.acelerati.gestionmatricula.domain.util.Validaciones.*;
import static com.acelerati.gestionmatricula.infraestructure.settings.Url.URL_GESTION_ACADEMICA;
import static com.acelerati.gestionmatricula.infraestructure.settings.Url.URL_GESTION_USUARIO;

@RestController
@RequestMapping("/estudianteCursos")
public class EstudianteCursoController {
    @Autowired
    private EstudianteCursoService estudianteCursoService;

    @PostMapping("/registrar/{idCurso}")
    public ResponseEntity<EstudianteCurso> registrar(@PathVariable("idCurso")Long idCurso, HttpSession session) {
        return new ResponseEntity<>(estudianteCursoService.registrarseEstudianteCurso(idCurso,session), HttpStatus.OK);
    }
    @GetMapping("/consultarHorarioVigente")
    public ResponseEntity<List<String>>consultarHorario(HttpSession session){
        return new ResponseEntity<>(estudianteCursoService.listaHorario(session),HttpStatus.OK);
    }
    @PutMapping("/subirPrevio")
    public ResponseEntity<EstudianteCurso> subirNota(@RequestBody EstudianteCurso estudianteCurso, HttpSession session){
          return new ResponseEntity<>(estudianteCursoService.subirNota(estudianteCurso,session),HttpStatus.OK);
    }






}

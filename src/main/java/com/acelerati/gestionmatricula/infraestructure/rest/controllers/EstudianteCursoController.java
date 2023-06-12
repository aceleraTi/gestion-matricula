package com.acelerati.gestionmatricula.infraestructure.rest.controllers;

import com.acelerati.gestionmatricula.application.service.interfaces.CursoService;
import com.acelerati.gestionmatricula.application.service.interfaces.EstudianteCursoService;
import com.acelerati.gestionmatricula.application.service.interfaces.EstudiantePensumService;
import com.acelerati.gestionmatricula.domain.model.*;
import com.acelerati.gestionmatricula.infraestructure.exceptions.NotFoundItemsInException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import static com.acelerati.gestionmatricula.domain.util.Validaciones.validarEstudiante;
import static com.acelerati.gestionmatricula.domain.util.Validaciones.validarLogged;

@RestController
@RequestMapping("/estudianteCursos")
public class EstudianteCursoController {
    @Autowired
    private EstudianteCursoService estudianteCursoService;
    @Autowired
    private CursoService cursoService;

    @PostMapping("/registrar/{idCurso}")
    public ResponseEntity<EstudianteCurso> registrar(@PathVariable("idCurso")Long idCurso, HttpSession session) {

        Materia materia=Materia.builder()
                .id(1L)
                .pensum(Pensum.builder().id(2L).build())
                .nombre("matematicas")
                .descripcion("Prueba materia")
                .materiaPrerequisito(Materia.builder().id(2L).build())
                .build();


        Estudiante estudiante= validarEstudiante(validarLogged("estudiante",session));

        Curso curso=cursoService.findById(idCurso);
        if(!curso.getEstado().equalsIgnoreCase("En Curso")){
            throw new NotFoundItemsInException("El curso esta cerrado");
        }
       EstudianteCurso estudianteCursoRegistrado=estudianteCursoService.registrarCurso(EstudianteCurso.builder()
                .estudiante(estudiante).curso(curso).build(),materia);
//no se ha validado las materias con prerrequisitos
        return new ResponseEntity<>(estudianteCursoRegistrado, HttpStatus.OK);
    }

}

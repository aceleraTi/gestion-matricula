package com.acelerati.gestionmatricula.infraestructure.rest.controllers;

import com.acelerati.gestionmatricula.application.service.interfaces.CursoService;
import com.acelerati.gestionmatricula.domain.model.Curso;
import com.acelerati.gestionmatricula.domain.model.Profesor;
import com.acelerati.gestionmatricula.domain.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import static com.acelerati.gestionmatricula.domain.util.Validaciones.validarLogged;
import static com.acelerati.gestionmatricula.domain.util.Validaciones.validarProfesor;


@RestController
@RequestMapping("/cursos")
public class CursoController {
    @Autowired
    private  CursoService cursoService;

    @PostMapping("/created")
    public ResponseEntity<Curso>crear(@RequestBody Curso curso,HttpSession session){

        validarLogged("director",session);
        Curso cursoCreado=cursoService.create(curso);
        return new ResponseEntity<>(cursoCreado, HttpStatus.CREATED);
    }

    @PutMapping("/asignarProfesor/{idCurso}/{idProfesor}")
    public ResponseEntity<Curso>asignarProfesor(@PathVariable("idCurso")Long idCurso,
                                                @PathVariable("idProfesor")Long idProfesor,
                                                HttpSession session){

        validarLogged("director",session);
        Profesor profesor=Profesor.builder().id(idProfesor).build();
        Curso curso=cursoService.findById(idCurso);
        curso.setProfesor(profesor);
        Curso cursoAct=cursoService.update(curso);
        return new ResponseEntity<>(cursoAct, HttpStatus.OK);
    }

    @GetMapping("/listar")
    public ResponseEntity<Page<Curso>> listarCursosProfesor(@RequestParam(name = "page",defaultValue = "0") int page,
                                                            HttpSession session){

        Profesor profesor= validarProfesor(validarLogged("profesor",session));
        Pageable pageRequest= PageRequest.of(page,3);
        Page<Curso> cursoPage=cursoService.findByProfesor(profesor,pageRequest);
        return new ResponseEntity<>(cursoPage,HttpStatus.OK);

    }












    @PostMapping("/usuario")
    public ResponseEntity<Usuario>setSession(@RequestBody Usuario usuario, HttpSession session){

        session.setAttribute("usuario",usuario);
        System.out.println(session.getAttribute("usuario"));
        return new ResponseEntity<>(usuario, HttpStatus.OK);

    }


}

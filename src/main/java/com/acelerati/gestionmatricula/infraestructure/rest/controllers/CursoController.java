package com.acelerati.gestionmatricula.infraestructure.rest.controllers;

import com.acelerati.gestionmatricula.application.service.interfaces.CursoService;
import com.acelerati.gestionmatricula.domain.model.Curso;
import com.acelerati.gestionmatricula.infraestructure.exceptions.NotLoggedInException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;



@RestController
@RequestMapping("/cursos")
public class CursoController {
    @Autowired
    private  CursoService cursoService;

    HttpSession session;


    @PostMapping("/created")
    public ResponseEntity<Curso>crear(@RequestBody Curso curso,HttpSession session){

        String usuario=(String) session.getAttribute("usuario");
        System.out.println(usuario);
        try {
            if(usuario.equals("director")){
                Curso cursoCreado=cursoService.create(curso);
                return new ResponseEntity<>(cursoCreado, HttpStatus.CREATED);
            }
            else{
                throw new NotLoggedInException("Usuario no autorizado");
            }
        }catch (NullPointerException e){
            throw new NotLoggedInException();
        }


    }


    @GetMapping("/usuario/{usuario}")
    public ResponseEntity<String>setSession(@PathVariable("usuario") String usuario,HttpSession session){

        session.setAttribute("usuario",usuario);
        System.out.println(session.getAttribute("usuario"));
        return new ResponseEntity<>(usuario, HttpStatus.OK);

    }


}

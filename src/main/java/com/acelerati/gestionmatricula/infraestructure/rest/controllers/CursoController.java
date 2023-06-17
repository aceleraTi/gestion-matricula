package com.acelerati.gestionmatricula.infraestructure.rest.controllers;

import com.acelerati.gestionmatricula.application.service.interfaces.CursoService;
import com.acelerati.gestionmatricula.application.service.interfaces.EstudianteCursoService;
import com.acelerati.gestionmatricula.application.service.interfaces.EstudianteCursoTareaService;
import com.acelerati.gestionmatricula.application.service.interfaces.TareaService;
import com.acelerati.gestionmatricula.domain.model.Curso;
import com.acelerati.gestionmatricula.domain.model.Profesor;
import com.acelerati.gestionmatricula.domain.model.Usuario;
import com.acelerati.gestionmatricula.infraestructure.exceptions.NotLoggedInException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;

import static com.acelerati.gestionmatricula.domain.util.Validaciones.validarLogged;
import static com.acelerati.gestionmatricula.domain.util.Validaciones.validarProfesor;
import static com.acelerati.gestionmatricula.infraestructure.settings.Url.URL_GESTION_USUARIO;


@RestController
@RequestMapping("/cursos")
public class CursoController {
    @Autowired
    private CursoService cursoService;
    @Autowired
    private RestTemplate cursoRestTemplate;

    @PostMapping("/created")
    public ResponseEntity<Curso> crear(@RequestBody Curso curso, HttpSession session) {
        return new ResponseEntity<>(cursoService.create(curso,session), HttpStatus.CREATED);
    }

    @PutMapping("/asignarProfesor/{idCurso}/{idProfesor}")
    public ResponseEntity<Curso> asignarProfesor(@PathVariable("idCurso") Long idCurso,
                                                 @PathVariable("idProfesor") Long idProfesor,
                                                 HttpSession session) {

        return new ResponseEntity<>(cursoService.asignarProfesor(idCurso,idProfesor,session), HttpStatus.OK);
    }

    @GetMapping("/listar")
    public ResponseEntity<Page<Curso>> listarCursosProfesor(@RequestParam(name = "page", defaultValue = "0") int page,
                                                            HttpSession session) {

        Usuario usuario=(Usuario) session.getAttribute("usuario");
        Profesor profesor = validarProfesor(validarLogged(3L,usuario));
        Pageable pageRequest = PageRequest.of(page, 2);
        Page<Curso> cursoPage = cursoService.findByProfesor(profesor, pageRequest);
        return new ResponseEntity<>(cursoPage, HttpStatus.OK);

    }

    @PutMapping("/cerrarCurso/{idCurso}")
    public ResponseEntity<Curso> cerrarCurso(@PathVariable("idCurso") Long idCurso, HttpSession session) {
        return new ResponseEntity<>(cursoService.cerrarCurso(idCurso,session), HttpStatus.OK);
    }


    @PostMapping("/usuario/{idUsuario}")
    public ResponseEntity<Usuario> setSession(@PathVariable("idUsuario") Long idUsuario, HttpSession session) {

        try{
            Usuario usuario1= cursoRestTemplate.getForObject(URL_GESTION_USUARIO+"/api/1.0/usuarios/"+idUsuario,Usuario.class);
            session.setAttribute("usuario", usuario1);
            System.out.println(session.getAttribute("usuario"));
            return new ResponseEntity<>(usuario1, HttpStatus.OK);
        }catch (HttpServerErrorException exception){
           throw new NotLoggedInException("Usuario no existe");
        }
    }


}

package com.acelerati.gestionmatricula.infraestructure.rest.controllers;

import com.acelerati.gestionmatricula.application.service.interfaces.CursoService;
import com.acelerati.gestionmatricula.domain.model.Curso;
import com.acelerati.gestionmatricula.domain.model.Profesor;
import com.acelerati.gestionmatricula.domain.model.Tarea;
import com.acelerati.gestionmatricula.domain.model.Usuario;
import com.acelerati.gestionmatricula.infraestructure.exceptions.NotCreatedInException;
import com.acelerati.gestionmatricula.infraestructure.exceptions.NotLoggedInException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import static com.acelerati.gestionmatricula.infraestructure.settings.Tipo_Usuarios.DIRECTOR;
import static com.acelerati.gestionmatricula.infraestructure.settings.Tipo_Usuarios.PROFESOR;
import static com.acelerati.gestionmatricula.infraestructure.settings.Url.URL_GESTION_USUARIO;


@RestController
@RequestMapping("/cursos")
@Api(tags = "Gestion de Curso",description = "Permite, Crear un curso, Asignar un profesor a un curso," +
        "Listar los cursos de un profesor y cerrar un curso.")
public class CursoController {
    @Autowired
    private CursoService cursoService;
    @Autowired
    private RestTemplate cursoRestTemplate;



    @PostMapping("/created")
    @Operation(summary = "Crea un nuevo curso", description ="Permite a un director crear un nuevo curso")
    public ResponseEntity<Curso> crear(@RequestBody Curso curso, HttpSession session) {
        validarLogged(DIRECTOR, (Usuario) session.getAttribute("usuario"));
        return new ResponseEntity<>(cursoService.create(curso), HttpStatus.CREATED);
    }

    @PutMapping("/asignarProfesor/{idCurso}/{idProfesor}")
    @Operation(summary = "Asigna un profesor a un curso"
            , description ="Permite a un director asignar un profesor a un curso")
    public ResponseEntity<Curso> asignarProfesor(@PathVariable("idCurso") Long idCurso,
                                                 @PathVariable("idProfesor") Long idProfesor,
                                                 HttpSession session) {
        validarLogged(DIRECTOR, (Usuario) session.getAttribute("usuario"));

        return new ResponseEntity<>(cursoService.asignarProfesor(idCurso,idProfesor), HttpStatus.OK);
    }

    @GetMapping("/listar")
    @Operation(summary = "Lista cursos de un profesor", description ="Permite a un profesor listar los cursos asignados")
    public ResponseEntity<Page<Curso>> listarCursosProfesor(@RequestParam(name = "page", defaultValue = "0") int page,
                                                            HttpSession session) {

        Usuario usuario=(Usuario) session.getAttribute("usuario");
        Profesor profesor = validarProfesor(validarLogged(PROFESOR,usuario));
        Pageable pageRequest = PageRequest.of(page, 2);
        Page<Curso> cursoPage = cursoService.findByProfesor(profesor, pageRequest);
        return new ResponseEntity<>(cursoPage, HttpStatus.OK);

    }

    @PutMapping("/cerrarCurso/{idCurso}")
    @Operation(summary = "Cerrar un curso", description ="Permite a un profesor cerrar un curso")
    public ResponseEntity<Curso> cerrarCurso(@PathVariable("idCurso") Long idCurso, HttpSession session) {
        Profesor profesor = validarProfesor(validarLogged(PROFESOR, (Usuario) session.getAttribute("usuario")));
        return new ResponseEntity<>(cursoService.cerrarCurso(idCurso,profesor), HttpStatus.OK);
    }


    @PostMapping("/usuario/{idUsuario}")
    @Operation(summary = "Iniciar Sesion", description ="Permite a un usuario iniciar sesion")
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

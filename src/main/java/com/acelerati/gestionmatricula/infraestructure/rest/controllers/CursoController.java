package com.acelerati.gestionmatricula.infraestructure.rest.controllers;

import com.acelerati.gestionmatricula.application.service.interfaces.CursoService;
import com.acelerati.gestionmatricula.application.service.interfaces.EstudianteCursoService;
import com.acelerati.gestionmatricula.application.service.interfaces.EstudianteCursoTareaService;
import com.acelerati.gestionmatricula.application.service.interfaces.TareaService;
import com.acelerati.gestionmatricula.domain.model.Curso;
import com.acelerati.gestionmatricula.domain.model.EstudianteCurso;
import com.acelerati.gestionmatricula.domain.model.Profesor;
import com.acelerati.gestionmatricula.domain.model.Usuario;
import com.acelerati.gestionmatricula.infraestructure.exceptions.NotCreatedInException;
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

import java.util.List;

import static com.acelerati.gestionmatricula.domain.util.Validaciones.validarLogged;
import static com.acelerati.gestionmatricula.domain.util.Validaciones.validarProfesor;
import static com.acelerati.gestionmatricula.infraestructure.settings.Url.URL_GESTION_USUARIO;


@RestController
@RequestMapping("/cursos")
public class CursoController {
    @Autowired
    private CursoService cursoService;
    @Autowired
    private EstudianteCursoService estudianteCursoService;
    @Autowired
    private TareaService tareaService;
    @Autowired
    private EstudianteCursoTareaService estudianteCursoTareaService;

    @Autowired
    private RestTemplate cursoRestTemplate;

    @PostMapping("/created")
    public ResponseEntity<Curso> crear(@RequestBody Curso curso, HttpSession session) {
        Usuario usuario=(Usuario) session.getAttribute("usuario");
        validarLogged(2L, usuario);
        Curso cursoCreado = cursoService.create(curso);
        return new ResponseEntity<>(cursoCreado, HttpStatus.CREATED);
    }



    @PutMapping("/asignarProfesor/{idCurso}/{idProfesor}")
    public ResponseEntity<Curso> asignarProfesor(@PathVariable("idCurso") Long idCurso,
                                                 @PathVariable("idProfesor") Long idProfesor,
                                                 HttpSession session) {

        Usuario usuario=(Usuario) session.getAttribute("usuario");
        validarLogged(2L, usuario);
        try{

        Usuario validarUsuario= cursoRestTemplate.getForObject(URL_GESTION_USUARIO+"/api/1.0/usuarios/"+idProfesor,Usuario.class);
        if(validarUsuario.getTipoUsuario()!=3){
            throw new NotLoggedInException("Este usuario no tiene rol de profesor");
        }
        Profesor profesor = new Profesor();
        profesor.setId(idProfesor);
        Curso curso = cursoService.findById(idCurso);
        curso.setProfesor(profesor);
        Curso cursoAct = cursoService.update(curso);
        return new ResponseEntity<>(cursoAct, HttpStatus.OK);
        }catch (HttpServerErrorException exception){
            throw new NotLoggedInException("Usuario no existe");
        }
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
    public ResponseEntity<List<EstudianteCurso>> cerrarCurso(@PathVariable("idCurso") Long idCurso, HttpSession session) {

        Usuario usuario=(Usuario) session.getAttribute("usuario");
        Profesor profesor = validarProfesor(validarLogged(3L, usuario));

        Curso curso = cursoService.findById(idCurso);
        if (curso.getProfesor().getId() != profesor.getId()) {
            throw new NotLoggedInException("No tiene permisos para cerrar el curso");
        }
        List<EstudianteCurso> estudianteCursos = estudianteCursoService.findByCurso(curso);
        for (EstudianteCurso estud : estudianteCursos) {

            if (estud.getPrevio1() == null ||
                    estud.getPrevio2() == null ||
                    estud.getPrevio4() == null) {
                throw new NotCreatedInException("Aun no se han subido todas las notas de los previos");
            } else if (estud.getNotaFinal() != null) {
                continue;
            }
            Double notaPrevio3 = tareaService.findByCursoId(idCurso)
                    .stream()
                    .mapToDouble(tar -> estudianteCursoTareaService.notaTarea(tar.getId(), estud.getId()))
                    .average()
                    .orElse(0.0);

            estud.setPrevio3(notaPrevio3);

            Double notaFinal = ((estud.getPrevio1() + estud.getPrevio2() +
                    estud.getPrevio3()) * 7/30) + (estud.getPrevio4() * 0.3);

            estud.setNotaFinal(notaFinal);
        }



        List<EstudianteCurso> estudianteCursosCerrados =  estudianteCursoService.guardarEstudiantesCursos(estudianteCursos);
        curso.setEstado("Cerrado");
        cursoService.update(curso);
        return new ResponseEntity<>(estudianteCursosCerrados, HttpStatus.OK);
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

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
    @Autowired
    private EstudianteCursoTareaService estudianteCursoTareaService;
    @Autowired
    private CursoService cursoService;
    @Autowired
    private HorarioService horarioService;

    @Autowired
    private EstudiantePensumService estudiantePensumService;

    @Autowired
    private TareaService tareaService;

    @Autowired
    private RestTemplate estudianteCursoRestTemplate;


    @PostMapping("/registrar/{idCurso}")
    public ResponseEntity<EstudianteCurso> registrar(@PathVariable("idCurso")Long idCurso, HttpSession session) {
        Usuario usuario=(Usuario) session.getAttribute("usuario");
        Estudiante estudiante= validarEstudiante(validarLogged(4L,usuario));


        Curso curso=cursoService.findById(idCurso);
        Materia materia=new Materia();
        try{
          Materia materia1=estudianteCursoRestTemplate.getForObject(URL_GESTION_ACADEMICA+"/materias/"+curso.getMateria().getId(),
                     Materia.class);
           materia=materia1;

        }catch (HttpServerErrorException exception){
            throw new NotFoundItemsInException("Materia no encontrada");

        }catch (HttpClientErrorException exception){
                  }
        System.out.println("Passsssssssssss");
        try {
            assert materia != null;
            if(Boolean.FALSE.equals(estudiantePensumService.findByIdPensum(materia.getPensum().getId()))){
                throw new NotFoundItemsInException("No estas matriculado en el programa academico de esta materia");
            }
        } catch (AssertionError e) {
           throw new NotFoundItemsInException("La materia no existe");
        }
        if(materia.getMateriaPrerequisito()!=null){
            List<EstudianteCurso> estudianteCursoList=estudianteCursoService.listarEstudianteCursos(estudiante);
            List<Curso> listCurso= cursoService.listCursos(materia.getMateriaPrerequisito());
         /*   List<EstudianteCurso> estudianteCursosPre=estudianteCursoList.stream()
                    .filter(estudianteCurso -> listCurso.stream()
                            .anyMatch(curso1 -> curso1.getId()==estudianteCurso.getCurso().getId()))
                    .filter(estudianteCurso -> estudianteCurso.getNotaFinal()>3.5)
                    .collect(Collectors.toList());*/

           if(!estudianteCursoList.stream().anyMatch(estudianteCurso -> listCurso.stream()
                   .anyMatch(curso1 -> curso1.getId() == estudianteCurso.getCurso().getId()
                                    && estudianteCurso.getNotaFinal() > 3.5))){
               throw new NotFoundItemsInException("Aun no has aprobado el prerrequisito de esta materia");
           }
        }
        if(!curso.getEstado().equalsIgnoreCase("En Curso")){
            throw new NotFoundItemsInException("El curso esta cerrado");
        }

        EstudianteCurso estudianteCurso=new EstudianteCurso();
        estudianteCurso.setEstudiante(estudiante);
        estudianteCurso.setCurso(curso);
        EstudianteCurso estudianteCursoRegistrado=estudianteCursoService.registrarCurso(estudianteCurso);

        return new ResponseEntity<>(estudianteCursoRegistrado, HttpStatus.OK);
    }

    @GetMapping("/consultarHorarioVigente")
    public ResponseEntity<List<String>>consultarHorario(HttpSession session){
        Usuario usuario=(Usuario) session.getAttribute("usuario");
        Estudiante estudiante= validarEstudiante(validarLogged(4L,usuario));

        List<Curso> cursoList=estudianteCursoService.listarCursos(estudiante);
        List<List<Horario>> horarioList = cursoList.stream()
                .map(curso -> horarioService.findByCurso(curso))
                .collect(Collectors.toList());

        List<String> horariosCursos = horarioList.stream()
                .flatMap(List::stream)
                .map(horario -> "Curso "+ horario.getCurso().getId() + " - Dia " + horario.getDia() + " - Hora " + horario.getHoraInicio())
                .collect(Collectors.toList());

        return new ResponseEntity<>(horariosCursos,HttpStatus.OK);
    }
    @PutMapping("/subirPrevio")
    public ResponseEntity<EstudianteCurso> subirNota(@RequestBody EstudianteCurso estudianteCurso, HttpSession session){
        Usuario usuario=(Usuario) session.getAttribute("usuario");
        Profesor profesor= validarProfesor(validarLogged(3L,usuario));
        if(estudianteCurso.getPrevio3()!=null){
            throw new NotCreatedInException("El previo 3 corresponde a la sumatoria de las tareas");
        }
        EstudianteCurso estudianteCursoConsulta=estudianteCursoService.findByEstudianteCursoId(estudianteCurso.getId());

        if(profesor.getId()!=estudianteCursoConsulta.getCurso().getProfesor().getId()){
            throw new NotCreatedInException("Este Curso no esta asignado a usted");
        }
        if(estudianteCurso.getPrevio1()!=null && estudianteCursoConsulta.getPrevio1()!=null ||
                estudianteCurso.getPrevio2()!=null && estudianteCursoConsulta.getPrevio2()!=null ||
                estudianteCurso.getPrevio4()!=null && estudianteCursoConsulta.getPrevio4()!=null ){
            throw new NotCreatedInException("Uno de los previos ya tiene  nota asignada");
        }
        if(estudianteCurso.getPrevio1()!=null)
            estudianteCursoConsulta.setPrevio1(estudianteCurso.getPrevio1());
        if(estudianteCurso.getPrevio2()!=null)
            estudianteCursoConsulta.setPrevio2(estudianteCurso.getPrevio2());
        if(estudianteCurso.getPrevio4()!=null)
            estudianteCursoConsulta.setPrevio4(estudianteCurso.getPrevio4());
        EstudianteCurso estudianteCursoPrevioRegistrado=estudianteCursoService.actualizarCursoEstudiante(estudianteCursoConsulta);
        return new ResponseEntity<>(estudianteCursoPrevioRegistrado,HttpStatus.OK);
    }






}

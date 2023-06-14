package com.acelerati.gestionmatricula.infraestructure.rest.controllers;

import com.acelerati.gestionmatricula.application.service.interfaces.CursoService;
import com.acelerati.gestionmatricula.application.service.interfaces.EstudianteCursoService;
import com.acelerati.gestionmatricula.application.service.interfaces.HorarioService;
import com.acelerati.gestionmatricula.domain.model.*;
import com.acelerati.gestionmatricula.infraestructure.exceptions.NotCreatedInException;
import com.acelerati.gestionmatricula.infraestructure.exceptions.NotFoundItemsInException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

import static com.acelerati.gestionmatricula.domain.util.Validaciones.*;

@RestController
@RequestMapping("/estudianteCursos")
public class EstudianteCursoController {
    @Autowired
    private EstudianteCursoService estudianteCursoService;
    @Autowired
    private CursoService cursoService;
    @Autowired
    private HorarioService horarioService;

    @PostMapping("/registrar/{idCurso}")
    public ResponseEntity<EstudianteCurso> registrar(@PathVariable("idCurso")Long idCurso, HttpSession session) {

        Materia materia=Materia.builder()
                .id(2L)
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

    @GetMapping("/consultarHorarioVigente")
    public ResponseEntity<List<String>>consultarHorario(HttpSession session){
        Estudiante estudiante= validarEstudiante(validarLogged("estudiante",session));

        List<Curso> cursoList=estudianteCursoService.listarEstudianteCurso(estudiante);
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

        Profesor profesor= validarProfesor(validarLogged("profesor",session));

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

        EstudianteCurso estudianteCursoPrevioRegistrado=estudianteCursoService.asignarNota(estudianteCursoConsulta);

        return new ResponseEntity<>(estudianteCursoPrevioRegistrado,HttpStatus.OK);
    }


}

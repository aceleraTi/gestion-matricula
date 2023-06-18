package com.acelerati.gestionmatricula.application.service;

import com.acelerati.gestionmatricula.application.service.interfaces.EstudianteCursoTareaService;
import com.acelerati.gestionmatricula.domain.model.*;
import com.acelerati.gestionmatricula.domain.model.repository.EstudianteCursoRepository;
import com.acelerati.gestionmatricula.domain.model.repository.EstudianteCursoTareaRepository;
import com.acelerati.gestionmatricula.domain.model.repository.TareaRepository;
import com.acelerati.gestionmatricula.infraestructure.entitys.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@DisplayName("Test Unitario para EstudianteCursoTareaServiceImplement")
public class EstudianteCursoTareaServiceImplementTest {
    @Mock
    private EstudianteCursoTareaRepository estudianteCursoTareaRepository;
    @Mock
    private TareaRepository tareaRepository;
    @Mock
    private EstudianteCursoRepository estudianteCursoRepository;
    EstudianteCursoTarea estudianteCursoTareaIn;
    EstudianteCursoTareaService estudianteCursoTareaService;

    @BeforeEach
    public void setup() {
        SemestreAcademico semestreAcademico = new SemestreAcademico();
        semestreAcademico.setId(1L);
        //******************************************
        Profesor profesor = new Profesor();
        profesor.setId(2L);
        //*******************************************
        Curso curso = new Curso();
        curso.setId(1L);
        curso.setProfesor(profesor);
        curso.setSemestreAcademico(semestreAcademico);
        //*******************************************
        Estudiante estudiante = new Estudiante();
        estudiante.setId(100L);
        //********************************************
        EstudianteCurso estudianteCurso = new EstudianteCurso();
        estudianteCurso.setId(3L);
        estudianteCurso.setEstudiante(estudiante);
        estudianteCurso.setCurso(curso);
        //********************************************
        Tarea tarea = new Tarea();
        tarea.setId(1L);
        tarea.setCurso(curso);
        tarea.setDescripcion("En esta tarea de Matemáticas, se explorarán los conceptos de proporciones y se resolverán problemas relacionados." +
                " Las proporciones son una herramienta fundamental para comparar magnitudes y establecer relaciones entre ellas");
        //*********************************************
        estudianteCursoTareaIn.setEstudianteCurso(estudianteCurso);
        estudianteCursoTareaIn.setTarea(tarea);
        estudianteCursoTareaIn.setNota(4.5);
        //*********************************************

        CursoEntity cursoEntityOut=CursoEntity.builder()
                .id(1L)
                .profesor(profesor)
                .semestreAcademicoEntity(SemestreAcademicoEntity.builder()
                        .id(1L)
                        .build())
                .build();
        //*********************************************
        TareaEntity tareaEntityOut=TareaEntity.builder()
                .id(1L)
                .curso(cursoEntityOut)
                .descripcion("En esta tarea de Matemáticas, se explorarán los conceptos de proporciones y se resolverán problemas relacionados." +
                        " Las proporciones son una herramienta fundamental para comparar magnitudes y establecer relaciones entre ellas")
                .build();
        //*********************************************
        EstudianteCursoEntity estudianteCursoEntityOut=EstudianteCursoEntity.builder()
                .id(3L)
                .estudiante(estudiante)
                .curso(cursoEntityOut)
                .build();
        //*********************************************
         EstudianteCursoTareaEntity estudianteCursoTareaEntityOut=EstudianteCursoTareaEntity.builder()
                 .id(10L)
                 .estudianteCurso(estudianteCursoEntityOut)
                 .tarea(tareaEntityOut)
                 .nota(4.5)
                 .build();
         //**********************************************
        MockitoAnnotations.openMocks(this);
        when(tareaRepository.findByTareaId(any(Long.class))).thenReturn(tareaEntityOut);

        when(estudianteCursoRepository.findByEstudianteCursoEntityId(any(Long.class)))
                .thenReturn(Optional.of(estudianteCursoEntityOut));

        when(estudianteCursoTareaRepository.subirNota(any(EstudianteCursoTareaEntity.class)))
                .thenReturn(estudianteCursoTareaEntityOut);

        estudianteCursoTareaService=new EstudianteCursoTareaServiceImplement(estudianteCursoTareaRepository,
                tareaRepository, estudianteCursoRepository );
    }

}

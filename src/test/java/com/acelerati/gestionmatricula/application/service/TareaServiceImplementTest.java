package com.acelerati.gestionmatricula.application.service;

import com.acelerati.gestionmatricula.application.service.interfaces.TareaService;
import com.acelerati.gestionmatricula.domain.model.*;
import com.acelerati.gestionmatricula.domain.model.repository.CursoRepository;
import com.acelerati.gestionmatricula.domain.model.repository.TareaRepository;
import com.acelerati.gestionmatricula.infraestructure.entitys.CursoEntity;
import com.acelerati.gestionmatricula.infraestructure.entitys.SemestreAcademicoEntity;
import com.acelerati.gestionmatricula.infraestructure.entitys.TareaEntity;
import com.acelerati.gestionmatricula.infraestructure.exceptions.NotCreatedInException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayName("Test Unitario para TareaServiceImplement")
public class TareaServiceImplementTest {

    @Mock
    private CursoRepository cursoRepository;
    @Mock
    private TareaRepository tareaRepository;
    private Tarea tareaIn;
    private TareaService tareaService;

    @BeforeEach
    public void setup() {
        SemestreAcademico semestreAcademico = new SemestreAcademico();
        semestreAcademico.setId(1L);
        Profesor profesor=new Profesor();
        profesor.setId(2L);
        Curso curso = new Curso();
        curso.setId(1L);
        curso.setSemestreAcademico(semestreAcademico);

        tareaIn=new Tarea();
        tareaIn.setCurso(curso);
        tareaIn.setDescripcion("En esta tarea de Matemáticas, se explorarán los conceptos de proporciones y se resolverán problemas relacionados." +
                " Las proporciones son una herramienta fundamental para comparar magnitudes y establecer relaciones entre ellas");

        CursoEntity cursoEntityOut = CursoEntity.builder()
                .id(1L)
                .materia(new Materia())
                .profesor(new Profesor())
                .semestreAcademicoEntity(SemestreAcademicoEntity.builder().id(1L).build())
                .grupo(10)
                .estado("En curso")
                .build();
        cursoEntityOut.setProfesor(profesor);
        TareaEntity tareaEntityOut=TareaEntity.builder()
                .id(100L)
                .curso(cursoEntityOut)
                .descripcion("En esta tarea de Matemáticas, se explorarán los conceptos de proporciones y se resolverán problemas relacionados." +
                        " Las proporciones son una herramienta fundamental para comparar magnitudes y establecer relaciones entre ellas")
                .build();

        MockitoAnnotations.openMocks(this);
        when(tareaRepository.crearTarea(any(TareaEntity.class))).thenReturn(tareaEntityOut);
        when(cursoRepository.findById(any(Long.class))).thenReturn(Optional.of(cursoEntityOut));
        when(cursoRepository.findByIdAndProfesorAndEstado(any(Long.class),any(Profesor.class),any(String.class)))
                .thenReturn(Optional.of(cursoEntityOut));
        tareaService = new TareaServiceImplement(tareaRepository, cursoRepository);
    }
    @Nested
    @DisplayName("Test Cuando se asigna el horario")
    class CuandoCreaCurso {
        @Test
        void deberiaCrearUnaTareaExitosa() {
            Profesor profesor=new Profesor();
            profesor.setId(2L);
            Tarea tarea = tareaService.crearTarea(tareaIn,profesor);
            assertEquals(100, tarea.getId());
            assertEquals(1, tarea.getCurso().getId());
            assertEquals("En esta tarea de Matemáticas, se explorarán los conceptos de proporciones y se resolverán problemas relacionados." +
                    " Las proporciones son una herramienta fundamental para comparar magnitudes y establecer relaciones entre ellas", tarea.getDescripcion());
            verify(tareaRepository, times(1)).crearTarea(any(TareaEntity.class));
        }
       @Test
        void deberiaFallarCrearTareaDescripcionExcedeLimiteMaximo() {
           Profesor profesor=new Profesor();
           profesor.setId(2L);
           tareaIn.setDescripcion("La tarea consiste en resolver una serie de problemas relacionados con la geometría. Los problemas abarcan diferentes temas como áreas y perímetros de figuras, identificación de propiedades de triángulos, cálculo de volúmenes, entre otros. Los estudiantes deben aplicar los conceptos y fórmulas aprendidas en clase para resolver cada problema de manera precisa y explicar los pasos seguidos en el proceso de solución. Además, se solicita que presenten los resultados de manera clara y ordenada, utilizando diagramas y fórmulas cuando sea necesario. La tarea busca fortalecer la comprensión de los conceptos geométricos y mejorar las habilidades de resolución de problemas de los estudiantes en el campo de las matemáticas.");
           assertThrows(NotCreatedInException.class, () -> tareaService
                    .crearTarea(tareaIn,profesor));
            verify(tareaRepository, never()).crearTarea(any(TareaEntity.class));
        }
        @Test
        void deberiaFallarCrearTareaDescripcionExcedeLimiteMinimo() {
            Profesor profesor=new Profesor();
            profesor.setId(2L);
            tareaIn.setDescripcion("La tarea consiste en resolver una serie de problemas relacionados con la geometría.");
            assertThrows(NotCreatedInException.class, () -> tareaService
                    .crearTarea(tareaIn,profesor));
            verify(tareaRepository, never()).crearTarea(any(TareaEntity.class));
        }
        @Test
        void deberiaFallarMasTresTareas(){
            Profesor profesor=new Profesor();
            profesor.setId(2L);
            when(tareaRepository.countTareaCurso(any(TareaEntity.class))).thenReturn(3);
            assertThrows(NotCreatedInException.class, () -> tareaService.crearTarea(tareaIn,profesor));
            verify(tareaRepository, never()).crearTarea(any(TareaEntity.class));
        }
        @Test
        void deberiaFallarEstadoCursoDiferenteENCURSO(){
            Profesor profesor=new Profesor();
            profesor.setId(2L);
            when(cursoRepository.findByIdAndProfesorAndEstado(any(Long.class),any(Profesor.class),any(String.class)))
                 .thenReturn(Optional.empty());
            assertThrows(NotCreatedInException.class, () -> tareaService.crearTarea(tareaIn,profesor));
            verify(tareaRepository, never()).crearTarea(any(TareaEntity.class));
        }

    }

}

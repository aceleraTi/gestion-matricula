package com.acelerati.gestionmatricula.application.service;

import com.acelerati.gestionmatricula.application.service.interfaces.HorarioService;
import com.acelerati.gestionmatricula.domain.model.*;
import com.acelerati.gestionmatricula.domain.model.repository.CursoRepository;
import com.acelerati.gestionmatricula.domain.model.repository.HorarioRepository;
import com.acelerati.gestionmatricula.infraestructure.entitys.CursoEntity;
import com.acelerati.gestionmatricula.infraestructure.entitys.HorarioEntity;
import com.acelerati.gestionmatricula.infraestructure.entitys.SemestreAcademicoEntity;
import com.acelerati.gestionmatricula.infraestructure.exceptions.NotCreatedInException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

@DisplayName("Test Unitario para HorarioServiceImplement")
public class HorarioServiceImplementTest {

    @Mock
    private CursoRepository cursoRepository;
    @Mock
    private HorarioRepository horarioRepository;
    private Horario horarioIn;
    private HorarioService horarioService;

    @BeforeEach
    public void setup() {
        SemestreAcademico semestreAcademico = new SemestreAcademico();
        semestreAcademico.setId(1L);
        Curso curso = new Curso();
        curso.setId(1L);
        curso.setSemestreAcademico(semestreAcademico);

        horarioIn = new Horario();
        horarioIn.setCurso(curso);
        horarioIn.getCurso().getSemestreAcademico().setId(1L);
        horarioIn.setHoraInicio(LocalTime.parse("09:00"));
        horarioIn.setHoraFin(LocalTime.parse("12:00"));
        horarioIn.setDia("Lunes");
        horarioIn.setLink("https://Matematicas");

        CursoEntity cursoEntity = CursoEntity.builder()
                .id(1L)
                .materia(new Materia())
                .profesor(new Profesor())
                .semestreAcademicoEntity(SemestreAcademicoEntity.builder().id(1L).build())
                .grupo(10)
                .estado("En curso")
                .build();

        HorarioEntity horarioEntityOut = HorarioEntity.builder()
                .id(100L)
                .curso(cursoEntity)
                .horaInicio(LocalTime.parse("09:00"))
                .horaFin(LocalTime.parse("12:00"))
                .dia("Lunes")
                .link("https://Matematicas").build();

        MockitoAnnotations.openMocks(this);
        when(horarioRepository.asignarHorario(any(HorarioEntity.class))).thenReturn(horarioEntityOut);
        when(cursoRepository.findById(any(Long.class))).thenReturn(Optional.of(cursoEntity));
        horarioService = new HorarioServiceImplement(horarioRepository, cursoRepository);
    }
    @Nested
    @DisplayName("Test Cuando se asigna el horario")
    class CuandoCreaCurso {
        @Test
        void deberiaAsignarHorarioExitoso() {
            Horario horario = horarioService.asignarHorario(horarioIn);
            assertEquals(100, horario.getId());
            assertEquals(1, horario.getCurso().getId());
            assertEquals(1, horario.getCurso().getSemestreAcademico().getId());
            assertEquals(LocalTime.parse("09:00"), horario.getHoraInicio());
            assertEquals(LocalTime.parse("12:00"), horario.getHoraFin());
            assertEquals("Lunes", horario.getDia());
            assertEquals("https://Matematicas", horario.getLink());
            verify(horarioRepository, times(1)).asignarHorario(any(HorarioEntity.class));
        }
        @Test
        void deberiaFallarAsignarHorarioCuandoExcede() {
            when(horarioRepository.countHorariosCurso(any(HorarioEntity.class))).thenReturn(5);
            assertThrows(NotCreatedInException.class, () -> horarioService.asignarHorario(horarioIn));
            verify(horarioRepository, never()).asignarHorario(any(HorarioEntity.class));
        }

    }

}

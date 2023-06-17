package com.acelerati.gestionmatricula.application.service;

import com.acelerati.gestionmatricula.application.service.interfaces.HorarioService;
import com.acelerati.gestionmatricula.domain.model.Horario;
import com.acelerati.gestionmatricula.domain.model.Materia;
import com.acelerati.gestionmatricula.domain.model.Profesor;
import com.acelerati.gestionmatricula.domain.model.repository.CursoRepository;
import com.acelerati.gestionmatricula.domain.model.repository.HorarioRepository;
import com.acelerati.gestionmatricula.infraestructure.entitys.CursoEntity;
import com.acelerati.gestionmatricula.infraestructure.entitys.HorarioEntity;
import com.acelerati.gestionmatricula.infraestructure.entitys.SemestreAcademicoEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mock;

import java.time.LocalTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
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
        horarioIn.getCurso().setId(1L);
        horarioIn.getCurso().getSemestreAcademico().setId(1L);
        horarioIn.setHoraInicio(LocalTime.parse("09:00"));
        horarioIn.setHoraFin(LocalTime.parse("12:00"));
        horarioIn.setDia("Lunes");
        horarioIn.setLink("https://Matematicas");

        CursoEntity cursoEntity=CursoEntity.builder()
                .id(1L)
                .materia(new Materia())
                .profesor(new Profesor())
                .semestreAcademicoEntity(SemestreAcademicoEntity.builder().id(1L).build())
                .grupo(10)
                .estado("En curso")
                .build();

        HorarioEntity horarioEntityOut=HorarioEntity.builder()
                .id(100L)
                .curso(cursoEntity)
                .horaInicio(LocalTime.parse("09:00"))
                .horaFin(LocalTime.parse("12:00"))
                .dia("Lunes")
                .link("https://Matematicas").build();


        openMocks(this);
        when(horarioRepository.asignarHorario(any(HorarioEntity.class))).thenReturn(horarioEntityOut);
        when(cursoRepository.findById(any(Long.class))).thenReturn(Optional.of(cursoEntity));
        horarioService=new HorarioServiceImplement(horarioRepository,cursoRepository);
    }

}

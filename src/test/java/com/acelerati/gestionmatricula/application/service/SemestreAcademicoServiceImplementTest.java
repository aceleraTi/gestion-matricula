package com.acelerati.gestionmatricula.application.service;


import com.acelerati.gestionmatricula.application.service.interfaces.SemestreAcademicoService;
import com.acelerati.gestionmatricula.domain.model.Horario;
import com.acelerati.gestionmatricula.domain.model.SemestreAcademico;
import com.acelerati.gestionmatricula.domain.model.repository.SemestreAcademicoRepository;
import com.acelerati.gestionmatricula.infraestructure.entitys.EstudiantePensumEntity;
import com.acelerati.gestionmatricula.infraestructure.entitys.HorarioEntity;
import com.acelerati.gestionmatricula.infraestructure.entitys.SemestreAcademicoEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.text.ParseException;
import java.text.SimpleDateFormat;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@DisplayName("Test Unitario para SemestreAcademicoServiceImplement")
public class SemestreAcademicoServiceImplementTest {

    @Mock
    private SemestreAcademicoRepository semestreAcademicoRepository;
    private SemestreAcademico semestreAcademicoIn;

    @InjectMocks
    private SemestreAcademicoServiceImplement semestreAcademicoServiceImplement;

    @BeforeEach
    public void setup() throws ParseException {
        semestreAcademicoIn=new SemestreAcademico();
        semestreAcademicoIn.setNumero(2);
        SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/dd");
        semestreAcademicoIn.setFechaInicio(formato.parse("2022/08/01"));
        semestreAcademicoIn.setFechaFin(formato.parse("2022/12/03"));
        semestreAcademicoIn.setAño(2022);


       SemestreAcademicoEntity semestreAcademicoEntityOut= SemestreAcademicoEntity.builder()
                .id(2L)
                .numero(2)
                .fechaInicio(formato.parse("2022/08/01"))
                .fechaFin(formato.parse("2022/12/03"))
                .año(2022)
                .build();

        MockitoAnnotations.openMocks(this);
        when(semestreAcademicoRepository.save(any(SemestreAcademicoEntity.class))).thenReturn(semestreAcademicoEntityOut);

    }
    @Nested
    @DisplayName("Test Cuando se guarda un semestre academico")
    class CuandoSeGuardaSemestreAcademico {

        @Test
        void deberiaGuardarSemestreAcademicoExitoso() {

            SemestreAcademico semestreAcademico=semestreAcademicoServiceImplement.create(semestreAcademicoIn);

            assertEquals(2, semestreAcademico.getId());
            assertEquals(2, semestreAcademico.getNumero());
            assertEquals(semestreAcademicoIn.getFechaInicio(), semestreAcademico.getFechaInicio());
            assertEquals(semestreAcademicoIn.getFechaFin(), semestreAcademico.getFechaFin());
            assertEquals(2022, semestreAcademico.getAño());
            verify(semestreAcademicoRepository, times(1)).save(any(SemestreAcademicoEntity.class));
        }
    }
}

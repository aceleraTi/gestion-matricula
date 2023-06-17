package com.acelerati.gestionmatricula.application.service;


import com.acelerati.gestionmatricula.application.service.interfaces.CursoService;
import com.acelerati.gestionmatricula.domain.model.Curso;
import com.acelerati.gestionmatricula.domain.model.repository.CursoRepository;
import com.acelerati.gestionmatricula.infraestructure.entitys.CursoEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@DisplayName("Test Unitario para CursoService")
public class CursoServiceTest {

    @Mock
    private CursoRepository cursoRepository;
    private Curso cursoIn;
    private CursoService cursoService;

    @BeforeEach
    public void setup() {
       /* cursoIn=Curso.builder()
                .idMateria(1L)
                .idProfesor(3L)
                .semestreAcademico(SemestreAcademico.builder().id(1L).build())
                .grupo(4)
                .build();

        CursoEntity cursoEntityOut=CursoEntity.builder()
                .id(100L)
                .idMateria(1L)
                .idProfesor(3L)
                .semestreAcademicoEntity(SemestreAcademicoEntity.builder().id(1L).build())
                .grupo(4)
                .build();
        openMocks(this);
        when(cursoRepository.save(any(CursoEntity.class))).thenReturn(cursoEntityOut);
        cursoService=new CursoServiceDefault(cursoRepository);*/

    }

    @Nested
    @DisplayName("Test Cuando se crea el curso")
    class CuandoCreaCurso{

        @Test
       void deberiaCrearCursoEntityExitoso(){
          //  Curso cursoCreado=cursoService.create(cursoIn);
          //  assertEquals(100,cursoCreado.getId());
            verify(cursoRepository).save(any(CursoEntity.class));

        }

    }



}

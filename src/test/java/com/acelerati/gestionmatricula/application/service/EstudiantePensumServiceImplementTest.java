package com.acelerati.gestionmatricula.application.service;

import com.acelerati.gestionmatricula.application.service.interfaces.EstudiantePensumService;
import com.acelerati.gestionmatricula.domain.model.*;
import com.acelerati.gestionmatricula.domain.model.repository.CursoRepository;
import com.acelerati.gestionmatricula.domain.model.repository.EstudianteCursoRepository;
import com.acelerati.gestionmatricula.domain.model.repository.EstudiantePensumRepository;
import com.acelerati.gestionmatricula.infraestructure.entitys.*;
import com.acelerati.gestionmatricula.infraestructure.exceptions.NotCreatedInException;
import com.acelerati.gestionmatricula.infraestructure.exceptions.NotFoundItemsInException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static com.acelerati.gestionmatricula.infraestructure.settings.Url.URL_GESTION_ACADEMICA;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@DisplayName("Test Unitario para EstudiantePensumServiceImplement")
public class EstudiantePensumServiceImplementTest {

    @Mock
    private  EstudiantePensumRepository estudiantePensumRepository;
    @Mock
    private  CursoRepository cursoRepository;
    @Mock
    private  EstudianteCursoRepository estudianteCursoRepository;
    @Mock
    private RestTemplate restTemplate;
    @InjectMocks
    private EstudiantePensumServiceImplement estudiantePensumService;

    EstudiantePensum estudiantePensumIn;



    @BeforeEach
    public void setup() {

        Estudiante estudiante=new Estudiante();
        estudiante.setId(425187L);
        estudiante.setNombre("Wilson");
        estudiante.setApellido("Arias");
        estudiante.setEmail("wilson@correo.com.co");
        estudiante.setCodigo("9632541885");
       //***************************************
        Pensum pensum=new Pensum();
        pensum.setId(15L);
        pensum.setAnio(2023);
        //**************************************
        estudiantePensumIn=new EstudiantePensum();
        estudiantePensumIn.setEstudiante(estudiante);
        estudiantePensumIn.setPensum(pensum);

        //******************************************
        //************************************
        //----------Lista de Materias--------
        Materia materia1=new Materia();
        materia1.setId(34L);
        materia1.setPensum(pensum);
        materia1.setNombre("Calculo 1");
        materia1.setDescripcion("Materia que se ve en el primer semestre de ingenieria, limites y derivadas");

        Materia materia2=new Materia();
        materia2.setId(38L);
        materia2.setPensum(pensum);
        materia2.setNombre("Calculo 2");
        materia2.setDescripcion("Materia que se ve en el segundo semestre de ingenieria, Integracion de funciones");
        materia2.setMateriaPrerequisito(materia1);

        Materia materia3=new Materia();
        materia3.setId(74L);
        materia3.setPensum(pensum);
        materia3.setNombre("Lengua Materna");
        materia3.setDescripcion("Materia que se ve en el primer semestre de ingenieria, Lectura y redaccion");

        List<Materia> materias=new ArrayList<>();
        materias.add(materia1);
        materias.add(materia2);
        materias.add(materia3);


        //****************************************
        SemestreAcademicoEntity semestreAcademico1 = SemestreAcademicoEntity.builder()
                .id(1L).build();
        SemestreAcademicoEntity semestreAcademico2 = SemestreAcademicoEntity.builder()
                .id(2L).build();

        CursoEntity curso1 = new CursoEntity();
        curso1.setId(1L);
        curso1.setSemestreAcademicoEntity(semestreAcademico1);
        curso1.setMateria(materia1);
        curso1.setEstado("Cerrado");

        CursoEntity curso2 = new CursoEntity();
        curso2.setId(2L);
        curso2.setSemestreAcademicoEntity(semestreAcademico2);
        curso2.setMateria(materia2);
        curso2.setEstado("En Curso");

        CursoEntity curso3 = new CursoEntity();
        curso3.setId(3L);
        curso3.setSemestreAcademicoEntity(semestreAcademico1);
        curso3.setMateria(materia3);
        curso3.setEstado("Cerrado");

        List<CursoEntity>cursoEntityList=new ArrayList<>();
        cursoEntityList.add(curso1);
        cursoEntityList.add(curso2);
        cursoEntityList.add(curso3);

        //****************************************
        EstudianteCursoEntity estudianteCursoEntityOut1=EstudianteCursoEntity.builder()
                .id(1L)
                .curso(curso1)
                .estudiante(estudiante)
                .previo1(4.5)
                .previo2(4.0)
                .previo3(3.1)
                .previo4(3.5)
                .notaFinal(3.75)
                .build();

        EstudianteCursoEntity estudianteCursoEntityOut2=EstudianteCursoEntity.builder()
                .id(2L)
                .curso(curso2)
                .estudiante(estudiante)
                .previo1(4.5)
                .previo2(4.0)
                .build();

        EstudianteCursoEntity estudianteCursoEntityOut3=EstudianteCursoEntity.builder()
                .id(3L)
                .curso(curso3)
                .estudiante(estudiante)
                .previo1(4.5)
                .previo2(4.0)
                .previo3(3.1)
                .previo4(5.0)
                .notaFinal(4.02)
                .build();


        //***************************************

        EstudiantePensumEntity estudiantePensumEntityOut=EstudiantePensumEntity.builder()
                .id(564L)
                .estudiante(estudiante)
                .pensum(pensum)
                .build();

        MockitoAnnotations.openMocks(this);
        when(estudiantePensumRepository.registrar(any(EstudiantePensumEntity.class))).thenReturn(estudiantePensumEntityOut);
        when(restTemplate.getForObject(any(String.class),eq(Pensum.class))).thenReturn(pensum);
        when(estudiantePensumRepository.countEstudiantePensum(any(EstudiantePensumEntity.class))).thenReturn(1);

        when(estudiantePensumRepository.findByPensumIdAndEstudianteId(any(Long.class),any(Long.class)))
                .thenReturn(Optional.of(estudiantePensumEntityOut));
        when(restTemplate.exchange(any(String.class), eq(HttpMethod.GET), eq(null),
                eq(new ParameterizedTypeReference<List<Materia>>() {})))
                .thenReturn(ResponseEntity.ok().body(materias));
        when(cursoRepository.listCursos(any(Materia.class))).thenReturn(cursoEntityList);
        when(estudianteCursoRepository.findByEstudianteIdAndCursoId(any(Long.class),any(Long.class)))
                .thenReturn(Optional.of(estudianteCursoEntityOut1))
                .thenReturn(Optional.of(estudianteCursoEntityOut2))
                .thenReturn(Optional.of(estudianteCursoEntityOut3));

    }
    @Nested
    @DisplayName("Test Cuando un estudiante se registra en un pensum")
    class CuandoSeRegitraEnPensum {
        @Test
        void deberiaRegistrarseExitosamenteEnPensum(){
            when(estudiantePensumRepository.findByPensumIdAndEstudianteId(any(Long.class),any(Long.class)))
                    .thenReturn(Optional.empty());
            EstudiantePensum estudiantePensum=estudiantePensumService.registrar(estudiantePensumIn);

            assertEquals(564L, estudiantePensum.getId());
            assertEquals(425187L, estudiantePensum.getEstudiante().getId());
            assertEquals(15L, estudiantePensum.getPensum().getId());
            verify(estudiantePensumRepository, times(1)).registrar(any(EstudiantePensumEntity.class));
        }
        @Test
        void deberiaFallarSiExcedeMasDosPensum(){
            when(estudiantePensumRepository.findByPensumIdAndEstudianteId(any(Long.class),any(Long.class)))
                    .thenReturn(Optional.empty());
            when(estudiantePensumRepository.countEstudiantePensum(any(EstudiantePensumEntity.class))).thenReturn(2);
            assertThrows(NotCreatedInException.class, () -> estudiantePensumService.registrar(estudiantePensumIn));
            verify(estudiantePensumRepository, never()).registrar(any(EstudiantePensumEntity.class));
        }
        @Test
        void deberiaFallarSiPensumNoExiste(){
            when(restTemplate.getForObject(any(String.class), eq(Pensum.class))).thenThrow(NotCreatedInException.class);
            assertThrows(NotCreatedInException.class, () -> estudiantePensumService.registrar(estudiantePensumIn));
            verify(estudiantePensumRepository, never()).registrar(any(EstudiantePensumEntity.class));

        }

        @Test
        void deberiaFallarSiPensumYaEstaMatriculado(){
            when(estudiantePensumRepository.findByPensumIdAndEstudianteId(any(Long.class),any(Long.class)))
                    .thenThrow(NotFoundItemsInException.class);
            assertThrows(NotFoundItemsInException.class, () -> estudiantePensumService.registrar(estudiantePensumIn));
            verify(estudiantePensumRepository, never()).registrar(any(EstudiantePensumEntity.class));
        }
    }
    @Nested
    @DisplayName("Test Cuando un estudiante consulta todas las materias que ha visto")
    class CuandoSeConsultanMaterias {
        @Test
        void deberiaConsultarMateriasExitosamente(){
            List<Materia> materiaList=estudiantePensumService.materiaList(estudiantePensumIn.getPensum().getId()
                    ,estudiantePensumIn.getEstudiante());

            assertEquals(34, materiaList.get(0).getId());
            assertEquals(74, materiaList.get(2).getId());
            verify(restTemplate,times(1)).exchange(any(String.class), eq(HttpMethod.GET), eq(null),
                    eq(new ParameterizedTypeReference<List<Materia>>() {}));
            verify(estudianteCursoRepository, times(3)).findByEstudianteIdAndCursoId
                    (any(Long.class),any(Long.class));

        }

        @Test
        void deberiaFallarSiNoObtieneMateriasDeConsulta(){
            when(restTemplate.exchange(any(String.class), eq(HttpMethod.GET), eq(null),
                    eq(new ParameterizedTypeReference<List<Materia>>() {})))
                    .thenThrow(NotFoundItemsInException.class);
            assertThrows(NotFoundItemsInException.class, () -> restTemplate.
                    exchange(URL_GESTION_ACADEMICA + "materias/pensum/" + estudiantePensumIn.getPensum().getId(),
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Materia>>() {}));
            verify(estudianteCursoRepository, never()).findByEstudianteIdAndCursoId
                    (any(Long.class),any(Long.class));

        }

        @Test
        void deberiaFallarSiNoEstaMatriculadoEnPensum(){
            when(estudiantePensumRepository.findByPensumIdAndEstudianteId(any(Long.class),any(Long.class)))
                    .thenThrow(HttpServerErrorException.class);

            assertThrows(HttpServerErrorException.class, () -> estudiantePensumService.materiaList
                    (estudiantePensumIn.getPensum().getId(),estudiantePensumIn.getEstudiante()));
            verify(restTemplate,never()).exchange(any(String.class), eq(HttpMethod.GET), eq(null),
                    eq(new ParameterizedTypeReference<List<Materia>>() {}));
        }

    }
}

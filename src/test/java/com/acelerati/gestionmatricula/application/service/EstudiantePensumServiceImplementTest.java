package com.acelerati.gestionmatricula.application.service;

import com.acelerati.gestionmatricula.application.service.interfaces.EstudiantePensumService;
import com.acelerati.gestionmatricula.domain.model.Estudiante;
import com.acelerati.gestionmatricula.domain.model.EstudiantePensum;
import com.acelerati.gestionmatricula.domain.model.Pensum;
import com.acelerati.gestionmatricula.domain.model.repository.CursoRepository;
import com.acelerati.gestionmatricula.domain.model.repository.EstudianteCursoRepository;
import com.acelerati.gestionmatricula.domain.model.repository.EstudiantePensumRepository;
import com.acelerati.gestionmatricula.infraestructure.entitys.EstudiantePensumEntity;
import com.acelerati.gestionmatricula.infraestructure.entitys.TareaEntity;
import com.acelerati.gestionmatricula.infraestructure.exceptions.NotCreatedInException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Optional;

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
      //  restTemplate = new RestTemplate();

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

        EstudiantePensumEntity estudiantePensumEntityOut=EstudiantePensumEntity.builder()
                .id(564L)
                .estudiante(estudiante)
                .pensum(pensum)
                .build();

        MockitoAnnotations.openMocks(this);
        when(estudiantePensumRepository.registrar(any(EstudiantePensumEntity.class))).thenReturn(estudiantePensumEntityOut);
        when(restTemplate.getForObject(any(String.class),eq(Pensum.class))).thenReturn(pensum);
        when(estudiantePensumRepository.countEstudiantePensum(any(EstudiantePensumEntity.class))).thenReturn(1);

    }
    @Nested
    @DisplayName("Test Cuando un estudiante se registra en un pensum")
    class CuandoSeRegitraEnPensum {
        @Test
        void deberiaRegistrarseExitosamenteEnPensum(){
            EstudiantePensum estudiantePensum=estudiantePensumService.registrar(estudiantePensumIn);
            assertEquals(564L, estudiantePensum.getId());
            assertEquals(425187L, estudiantePensum.getEstudiante().getId());
            assertEquals(15L, estudiantePensum.getPensum().getId());
            verify(estudiantePensumRepository, times(1)).registrar(any(EstudiantePensumEntity.class));
        }
        @Test
        void deberiaFallarSiExcedeMasDosPensum(){
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
    }
    @Nested
    @DisplayName("Test Cuando un estudiante consulta todas las materias que ha visto")
    class CuandoSeConsultanMaterias {

        @Test
        void deberiaConsultarMateriasExitosamente(){

        }

    }
}

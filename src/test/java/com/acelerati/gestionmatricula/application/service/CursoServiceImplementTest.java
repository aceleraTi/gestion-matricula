package com.acelerati.gestionmatricula.application.service;

import com.acelerati.gestionmatricula.domain.model.Curso;
import com.acelerati.gestionmatricula.domain.model.Materia;
import com.acelerati.gestionmatricula.domain.model.Profesor;
import com.acelerati.gestionmatricula.domain.model.SemestreAcademico;
import com.acelerati.gestionmatricula.domain.model.Usuario;
import com.acelerati.gestionmatricula.domain.model.repository.CursoRepository;
import com.acelerati.gestionmatricula.infraestructure.entitys.CursoEntity;
import com.acelerati.gestionmatricula.infraestructure.entitys.SemestreAcademicoEntity;
;
import com.acelerati.gestionmatricula.infraestructure.exceptions.NotCreatedInException;
import com.acelerati.gestionmatricula.infraestructure.exceptions.NotLoggedInException;
import com.acelerati.gestionmatricula.infraestructure.settings.Tipo_Usuarios;
import io.swagger.models.HttpMethod;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestTemplate;



@ExtendWith(MockitoExtension.class)
public class CursoServiceImplementTest {

    @InjectMocks
    CursoServiceImplement cursoService;
    @Mock
    CursoRepository cursoRepository;
    @Mock
    RestTemplate restTemplate;

    private Curso curso;

    @Test
    @DisplayName("Operacion de crear curso caso exitoso.")
    void deberiaCrearUnCursoExitoso() {

        SemestreAcademico semestreAcademico = new SemestreAcademico();
        semestreAcademico.setId(1L);
        Profesor profesor = new Profesor();
        profesor.setId(2L);
        Materia materia = new Materia();
        materia.setId(1L);

        curso = new Curso();
        curso.setId(1L);
        curso.setSemestreAcademico(semestreAcademico);
        curso.setMateria(materia);
        curso.setGrupo(1);
        curso.setProfesor(profesor);
        curso.setEstado("En curso");

        CursoEntity cursoEntityOut = CursoEntity.builder()
                .id(1L)
                .materia(curso.getMateria())
                .profesor(curso.getProfesor())
                .semestreAcademicoEntity(SemestreAcademicoEntity.builder().id(curso.getSemestreAcademico().getId()).build())
                .grupo(curso.getGrupo())
                .estado(curso.getEstado())
                .build();

        when(cursoRepository.esGrupoUnicoMateriaSemetre(curso.getGrupo(), curso.getMateria(),
                SemestreAcademicoEntity.builder().id(curso.getSemestreAcademico().getId()).build())).thenReturn(true);

        when(cursoRepository.countProfesorCurso(cursoEntityOut)).thenReturn(true);
        when(cursoRepository.save(any(CursoEntity.class))).thenReturn(cursoEntityOut);
        Curso cursoExi = cursoService.create(curso);
        assertEquals(1L, cursoExi.getId());

        verify(cursoRepository, times(1)).save(any(CursoEntity.class));

    }

    @Test
    @DisplayName("Operacion de crear curso caso GrupoUnicoMateriaSemetre.")
    void crearCursoCasoGrupoUnicoMateriaSemetre() {

        SemestreAcademico semestreAcademico = new SemestreAcademico();
        semestreAcademico.setId(1L);
        Profesor profesor = new Profesor();
        profesor.setId(2L);
        Materia materia = new Materia();
        materia.setId(1L);

        curso = new Curso();
        curso.setId(1L);
        curso.setSemestreAcademico(semestreAcademico);
        curso.setMateria(materia);
        curso.setGrupo(1);
        curso.setProfesor(profesor);
        curso.setEstado("En curso");

        assertThrows(NotCreatedInException.class, () -> cursoService
                .create(curso));
        verify(cursoRepository, never()).save(any(CursoEntity.class));

    }

    @Test
    @DisplayName("Operacion de crear curso caso CantidadCursosProfesor.")
    void crearCursoCasoCantidadCursosProfesor() {

        SemestreAcademico semestreAcademico = new SemestreAcademico();
        semestreAcademico.setId(1L);
        Profesor profesor = new Profesor();
        profesor.setId(2L);
        Materia materia = new Materia();
        materia.setId(1L);

        curso = new Curso();
        curso.setId(1L);
        curso.setSemestreAcademico(semestreAcademico);
        curso.setMateria(materia);
        curso.setGrupo(1);
        curso.setProfesor(profesor);
        curso.setEstado("En curso");

        when(cursoRepository.esGrupoUnicoMateriaSemetre(curso.getGrupo(), curso.getMateria(),
                SemestreAcademicoEntity.builder().id(curso.getSemestreAcademico().getId()).build())).thenReturn(true);

        assertThrows(NotCreatedInException.class, () -> cursoService
                .create(curso));
        verify(cursoRepository, never()).save(any(CursoEntity.class));

    }

    @Test
    @DisplayName("Operacion de asignar profesor caso exitoso.")
    void asignarProfesorExitoso() {

        SemestreAcademico semestreAcademico = new SemestreAcademico();
        semestreAcademico.setId(1L);
        Profesor profesor = new Profesor();
        profesor.setId(2L);
        Materia materia = new Materia();
        materia.setId(1L);

        curso = new Curso();
        curso.setId(1L);
        curso.setSemestreAcademico(semestreAcademico);
        curso.setMateria(materia);
        curso.setGrupo(1);
        curso.setProfesor(profesor);
        curso.setEstado("En curso");

        CursoEntity cursoEntityOut = CursoEntity.builder()
                .id(1L)
                .materia(curso.getMateria())
                .profesor(curso.getProfesor())
                .semestreAcademicoEntity(SemestreAcademicoEntity.builder().id(curso.getSemestreAcademico().getId()).build())
                .grupo(curso.getGrupo())
                .estado(curso.getEstado())
                .build();

        Long idProfesor = 1L;
        Long idCurso = 1L;
        String fooResourceUrl = "http://localhost:9080//api/1.0/usuarios/" + idProfesor;

        Usuario user = new Usuario();
        user.setUsuarioId(1L);
        user.setTipoUsuario(3L);

        when(this.restTemplate.getForObject(fooResourceUrl, Usuario.class)).thenReturn(user);
        when(cursoRepository.findById(idCurso)).thenReturn(Optional.of(cursoEntityOut));
        when(cursoRepository.countProfesorCurso(cursoEntityOut)).thenReturn(true);

        when(cursoRepository.update(any(CursoEntity.class))).thenReturn(cursoEntityOut);
        Curso cursoExi = cursoService.asignarProfesor(1L, 1L);
        assertEquals(1L, cursoExi.getId());

        verify(cursoRepository, times(1)).update(any(CursoEntity.class));

    }

    @Test
    @DisplayName("Operacion de asignar profesor caso casoValidarUsuarioEsProfesor.")
    void asignarProfesorCasoValidarUsuarioEsProfesor() {
        Long idProfesor = 1L;
        Long idCurso = 1L;
        String fooResourceUrl = "http://localhost:9080//api/1.0/usuarios/" + idProfesor;

        Usuario user = new Usuario();
        user.setUsuarioId(1L);
        user.setTipoUsuario(2L);

        when(this.restTemplate.getForObject(fooResourceUrl, Usuario.class)).thenReturn(user);
        assertThrows(NotLoggedInException.class, () -> cursoService
                .asignarProfesor(idCurso, idProfesor));
        verify(cursoRepository, never()).update(any(CursoEntity.class));

    }

    @Test
    @DisplayName("Operacion de asignar profesor caso curso no existe.")
    void asignarProfesorCasoCursoNoExiste() {

        Long idProfesor = 1L;
        Long idCurso = 1L;
        String fooResourceUrl = "http://localhost:9080//api/1.0/usuarios/" + idProfesor;

        Usuario user = new Usuario();
        user.setUsuarioId(1L);
        user.setTipoUsuario(3L);

        when(this.restTemplate.getForObject(fooResourceUrl, Usuario.class)).thenReturn(user);

        assertThrows(NotLoggedInException.class, () -> cursoService
                .asignarProfesor(idCurso, idProfesor));
        verify(cursoRepository, never()).update(any(CursoEntity.class));

    }

    @Test
    @DisplayName("Operacion de asignar profesor caso validarCantidadCursosProfesor.")
    void asignarProfesorCasoCantidadCursosProfesor() {

        SemestreAcademico semestreAcademico = new SemestreAcademico();
        semestreAcademico.setId(1L);
        Profesor profesor = new Profesor();
        profesor.setId(2L);
        Materia materia = new Materia();
        materia.setId(1L);

        curso = new Curso();
        curso.setId(1L);
        curso.setSemestreAcademico(semestreAcademico);
        curso.setMateria(materia);
        curso.setGrupo(1);
        curso.setProfesor(profesor);
        curso.setEstado("En curso");

        CursoEntity cursoEntityOut = CursoEntity.builder()
                .id(1L)
                .materia(curso.getMateria())
                .profesor(curso.getProfesor())
                .semestreAcademicoEntity(SemestreAcademicoEntity.builder().id(curso.getSemestreAcademico().getId()).build())
                .grupo(curso.getGrupo())
                .estado(curso.getEstado())
                .build();

        Long idProfesor = 1L;
        Long idCurso = 1L;
        String fooResourceUrl = "http://localhost:9080//api/1.0/usuarios/" + idProfesor;

        Usuario user = new Usuario();
        user.setUsuarioId(1L);
        user.setTipoUsuario(3L);

        when(this.restTemplate.getForObject(fooResourceUrl, Usuario.class)).thenReturn(user);
        when(cursoRepository.findById(idCurso)).thenReturn(Optional.of(cursoEntityOut));
        when(cursoRepository.countProfesorCurso(cursoEntityOut)).thenReturn(false);

        assertThrows(NotCreatedInException.class, () -> cursoService
                .asignarProfesor(idCurso, idProfesor));
        verify(cursoRepository, never()).update(any(CursoEntity.class));

    }
}

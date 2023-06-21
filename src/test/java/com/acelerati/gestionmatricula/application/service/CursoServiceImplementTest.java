package com.acelerati.gestionmatricula.application.service;

import com.acelerati.gestionmatricula.domain.model.*;
import com.acelerati.gestionmatricula.domain.model.repository.CursoRepository;
import com.acelerati.gestionmatricula.domain.model.repository.EstudianteCursoRepository;
import com.acelerati.gestionmatricula.domain.model.repository.TareaRepository;
import com.acelerati.gestionmatricula.infraestructure.entitys.CursoEntity;
import com.acelerati.gestionmatricula.infraestructure.entitys.EstudianteCursoEntity;
import com.acelerati.gestionmatricula.infraestructure.entitys.SemestreAcademicoEntity;
import com.acelerati.gestionmatricula.infraestructure.exceptions.NotCreatedInException;
import com.acelerati.gestionmatricula.infraestructure.exceptions.NotFoundItemsInException;
import com.acelerati.gestionmatricula.infraestructure.exceptions.NotLoggedInException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
public class CursoServiceImplementTest {

    @InjectMocks
    CursoServiceImplement cursoService;
    @Mock
    CursoRepository cursoRepository;
    @Mock
    EstudianteCursoRepository estudianteCursoRepository;
    @Mock
    TareaRepository tareaRepository;
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
        String fooResourceUrl = "http://localhost:9080//api/v1/usuarios/" + idProfesor;

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
        String fooResourceUrl = "http://localhost:9080//api/v1/usuarios/" + idProfesor;

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
        String fooResourceUrl = "http://localhost:9080//api/v1/usuarios/" + idProfesor;

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
        String fooResourceUrl = "http://localhost:9080//api/v1/usuarios/" + idProfesor;

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

    @Test
    @DisplayName("Operacion de cerrar curso caso exitoso.")
    void deberiaCerrarUnCursoExitoso() {

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

        Long idCurso = 1L;

        List<EstudianteCursoEntity> lstEstudiantes = new ArrayList<>();
        EstudianteCursoEntity estudiante1 = new EstudianteCursoEntity();
        estudiante1.setId(1L);
        estudiante1.setPrevio1(4.5);
        estudiante1.setPrevio2(4.5);
        estudiante1.setPrevio4(4.5);
        lstEstudiantes.add(estudiante1);
        EstudianteCursoEntity estudiante2 = new EstudianteCursoEntity();
        estudiante2.setId(2L);
        estudiante2.setPrevio1(4.5);
        estudiante2.setPrevio2(4.5);
        estudiante2.setPrevio4(4.5);
        lstEstudiantes.add(estudiante2);
        EstudianteCursoEntity estudiante3 = new EstudianteCursoEntity();
        estudiante3.setId(3L);
        estudiante3.setPrevio1(4.5);
        estudiante3.setPrevio2(4.5);
        estudiante3.setPrevio4(4.5);
        lstEstudiantes.add(estudiante3);

        when(cursoRepository.findById(idCurso)).thenReturn(Optional.of(cursoEntityOut));
        when(estudianteCursoRepository.findByCurso(cursoEntityOut)).thenReturn(lstEstudiantes);
        when(cursoRepository.update(any(CursoEntity.class))).thenReturn(cursoEntityOut);
        Curso cursoExi = cursoService.cerrarCurso(idCurso, profesor);
        assertEquals(1L, cursoExi.getId());

        verify(cursoRepository, times(1)).update(any(CursoEntity.class));

    }

    @Test
    @DisplayName("Operacion de cerrar curso caso curso no existe.")
    void cerrarUnCursoCasoCursoNoExiste() {

        Profesor profesor = new Profesor();
        profesor.setId(2L);
        Long idCurso = 1L;

        assertThrows(NoSuchElementException.class, () -> cursoService
                .cerrarCurso(idCurso, profesor));
        verify(cursoRepository, never()).update(any(CursoEntity.class));

    }

    @Test
    @DisplayName("Operacion de cerrar curso caso No tiene permisos para cerrar.")
    void cerrarUnCursoCasoNopermisosParaCerrar() {

        SemestreAcademico semestreAcademico = new SemestreAcademico();
        semestreAcademico.setId(1L);
        Profesor profesor = new Profesor();
        profesor.setId(5L);
        Profesor profesorM = new Profesor();
        profesorM.setId(6L);
        Materia materia = new Materia();
        materia.setId(1L);

        curso = new Curso();
        curso.setId(1L);
        curso.setSemestreAcademico(semestreAcademico);
        curso.setMateria(materia);
        curso.setGrupo(1);
        curso.setProfesor(profesorM);
        curso.setEstado("En curso");

        CursoEntity cursoEntityOut = CursoEntity.builder()
                .id(1L)
                .materia(curso.getMateria())
                .profesor(curso.getProfesor())
                .semestreAcademicoEntity(SemestreAcademicoEntity.builder().id(curso.getSemestreAcademico().getId()).build())
                .grupo(curso.getGrupo())
                .estado(curso.getEstado())
                .build();

        Long idCurso = 1L;

        when(cursoRepository.findById(idCurso)).thenReturn(Optional.of(cursoEntityOut));

        assertThrows(NotLoggedInException.class, () -> cursoService
                .cerrarCurso(idCurso, profesor));
        verify(cursoRepository, never()).update(any(CursoEntity.class));

    }

    @Test
    @DisplayName("Operacion de cerrar curso caso no se han subido notas.")
    void cerrarUnCursoCasoNoSeHanSubidoNotas() {

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

        Long idCurso = 1L;

        List<EstudianteCursoEntity> lstEstudiantes = new ArrayList<>();
        EstudianteCursoEntity estudiante1 = new EstudianteCursoEntity();
        estudiante1.setId(1L);
        lstEstudiantes.add(estudiante1);
        EstudianteCursoEntity estudiante2 = new EstudianteCursoEntity();
        estudiante2.setId(2L);
        lstEstudiantes.add(estudiante2);
        EstudianteCursoEntity estudiante3 = new EstudianteCursoEntity();
        estudiante3.setId(3L);
        lstEstudiantes.add(estudiante3);

        when(cursoRepository.findById(idCurso)).thenReturn(Optional.of(cursoEntityOut));
        when(estudianteCursoRepository.findByCurso(cursoEntityOut)).thenReturn(lstEstudiantes);
        assertThrows(NotCreatedInException.class, () -> cursoService
                .cerrarCurso(idCurso, profesor));
        verify(cursoRepository, never()).update(any(CursoEntity.class));

    }

    @Test
    @DisplayName("Operacion de listar los cursos de un profesor.")
    void deberiaEncontrarLosCursosPorProfesor() {

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

        List<CursoEntity> lstCursoEntitys = new ArrayList<>();
        lstCursoEntitys.add(cursoEntityOut);
        Page<CursoEntity> cursoEntityPage = new PageImpl(lstCursoEntitys);
        int page = 0;

        Pageable pageRequest = PageRequest.of(page, 2);

        when(cursoRepository.findByProfesor(profesor, pageRequest)).thenReturn(cursoEntityPage);
        Page<Curso> result = cursoService.findByProfesor(profesor, pageRequest);
        assertEquals(result.getSize(), cursoEntityPage.getSize());

    }


    @Test
    @DisplayName("Operacion de listar los cursos de un profesor caso nohay cursos.")
    void cursosPorProfesorCasoNoHayCursos() {

        Profesor profesor = new Profesor();
        profesor.setId(2L);

        List<CursoEntity> lstCursoEntitys = new ArrayList<>();
        Page<CursoEntity> cursoEntityPage = new PageImpl(lstCursoEntitys);
        int page = 0;
        Pageable pageRequest = PageRequest.of(page, 2);
        when(cursoRepository.findByProfesor(profesor, pageRequest)).thenReturn(cursoEntityPage);

        assertThrows(NotFoundItemsInException.class, () -> cursoService
                .findByProfesor(profesor, pageRequest));
    }


    @Test
    @DisplayName("Operacion de encontrar un curso caso exitoso.")
    void encontrarUnCursoExitoso() {

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

        Long idCurso = 1L;

        when(cursoRepository.findById(idCurso)).thenReturn(Optional.of(cursoEntityOut));
        Curso cursoExi = cursoService.findById(idCurso);
        assertEquals(idCurso, cursoExi.getId());
    }

    @Test
    @DisplayName("Operacion de encontrar un curso caso no existe.")
    void encontrarUnCursoCasoNoExiste() {

        Long idCurso = 1L;
        assertThrows(NoSuchElementException.class, () -> cursoService
                .findById(idCurso));
    }

}
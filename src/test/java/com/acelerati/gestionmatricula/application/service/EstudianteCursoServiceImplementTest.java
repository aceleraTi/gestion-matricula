package com.acelerati.gestionmatricula.application.service;

import com.acelerati.gestionmatricula.domain.model.*;
import com.acelerati.gestionmatricula.domain.model.repository.CursoRepository;
import com.acelerati.gestionmatricula.domain.model.repository.EstudianteCursoRepository;
import com.acelerati.gestionmatricula.domain.model.repository.EstudiantePensumRepository;
import com.acelerati.gestionmatricula.infraestructure.entitys.CursoEntity;
import com.acelerati.gestionmatricula.infraestructure.entitys.EstudianteCursoEntity;
import com.acelerati.gestionmatricula.infraestructure.entitys.EstudiantePensumEntity;
import com.acelerati.gestionmatricula.infraestructure.entitys.SemestreAcademicoEntity;
import com.acelerati.gestionmatricula.infraestructure.exceptions.NotCreatedInException;
import com.acelerati.gestionmatricula.infraestructure.exceptions.NotFoundItemsInException;
import com.acelerati.gestionmatricula.infraestructure.exceptions.NotLoggedInException;
import com.acelerati.gestionmatricula.infraestructure.settings.Url;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

import static com.acelerati.gestionmatricula.application.service.util.UtilEstudianteCurso.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class EstudianteCursoServiceImplementTest {

    @InjectMocks
    private EstudianteCursoServiceImplement estudianteCursoCasoUso;

    @Mock
    private EstudianteCursoRepository estudianteCursoRepository;
    @Mock
    private CursoRepository cursoRepository;
    @Mock
    private EstudiantePensumRepository estudiantePensumRepository;

    @Spy
    private RestTemplate restTemplate;


    @BeforeEach
    public void setup() {

        MockitoAnnotations.openMocks(this);
    }


    @Test
    void cuandoSeRegistraUnEstudianteEnUnCursoQueNoExiste() {

        Materia materia = new Materia();
        when(this.cursoRepository.findById(any())).thenReturn(Optional.empty());
        NotFoundItemsInException exception = assertThrows(NotFoundItemsInException.class,
                () -> this.estudianteCursoCasoUso.registrarseEstudianteCurso(1L, new Estudiante()));

        assertNotNull(exception);
        assertEquals(CURSO_NO_EXISTE, exception.getMessage());
    }

    @Test
    void cuandoSeRegistraUnEstudianteEnUnCursoQueExiste() {
        Materia materia = new Materia();
        materia.setId(1L);
        CursoEntity cursoEntity = new CursoEntity();
        cursoEntity.setId(1L);
        cursoEntity.setMateria(materia);
        Optional<CursoEntity> op = Optional.of(cursoEntity);
        when(this.cursoRepository.findById(any())).thenReturn(op);
        MockRestServiceServer server = MockRestServiceServer.createServer(restTemplate);
        server.expect(MockRestRequestMatchers.requestTo(Url.URL_GESTION_ACADEMICA +
                        "materias/" + materia.getId()))
                .andRespond((response) -> {
                    throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
                });
        NotFoundItemsInException exception = assertThrows(NotFoundItemsInException.class,
                () -> this.estudianteCursoCasoUso.registrarseEstudianteCurso(1L,
                        new Estudiante()));
        assertNotNull(exception);
        assertEquals(MATERIA_NO_ENCONTRADA, exception.getMessage());
    }


    @Test
    void cuandoSeRegistraUnEstudianteEnUnCursoYLaMateriaNoSeEncuentra() {
        Materia materia = new Materia();
        materia.setId(1L);
        CursoEntity cursoEntity = new CursoEntity();
        cursoEntity.setId(1L);
        cursoEntity.setMateria(materia);
        Optional<CursoEntity> op = Optional.of(cursoEntity);
        when(this.cursoRepository.findById(any())).thenReturn(op);
        MockRestServiceServer server = MockRestServiceServer.createServer(restTemplate);
        server.expect(MockRestRequestMatchers.requestTo(Url.URL_GESTION_ACADEMICA +
                        "materias/" + materia.getId()))
                .andRespond((response) -> {
                    throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
                });
        NotFoundItemsInException exception = assertThrows(NotFoundItemsInException.class,
                () -> this.estudianteCursoCasoUso.registrarseEstudianteCurso(1L,
                        new Estudiante()));
        assertNotNull(exception);
        assertEquals(MATERIA_NO_ENCONTRADA, exception.getMessage());
    }


    @Test
    void cuandoSeRegistraUnEstudianteEnUnCursoYLaMateriaNoExiste() {
        Materia materia = new Materia();
        materia.setId(1L);
        CursoEntity cursoEntity = new CursoEntity();
        cursoEntity.setId(1L);
        cursoEntity.setMateria(materia);
        Optional<CursoEntity> op = Optional.of(cursoEntity);
        when(this.cursoRepository.findById(any())).thenReturn(op);
        MockRestServiceServer server = MockRestServiceServer.createServer(restTemplate);
        server.expect(MockRestRequestMatchers.requestTo(Url.URL_GESTION_ACADEMICA +
                        "materias/" + materia.getId()))
                .andRespond((response) -> {
                    throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
                });
        NotFoundItemsInException exception = assertThrows(NotFoundItemsInException.class,
                () -> this.estudianteCursoCasoUso.registrarseEstudianteCurso(1L,
                        new Estudiante()));
        assertNotNull(exception);
        assertEquals(MATERIA_NO_EXISTE, exception.getMessage());
    }

    @Test
    void cuandoSeRegistraUnEstudianteEnUnCursoYLaMateriaEsDeOtroPensum() throws JsonProcessingException {
        Pensum pensum = new Pensum();
        pensum.setId(1L);
        Materia materia = new Materia();
        materia.setId(1L);
        materia.setPensum(pensum);
        CursoEntity cursoEntity = new CursoEntity();
        cursoEntity.setId(1L);
        cursoEntity.setMateria(materia);
        Optional<CursoEntity> op = Optional.of(cursoEntity);
        when(this.cursoRepository.findById(any())).thenReturn(op);
        when(this.estudiantePensumRepository.findByPensumIdAndEstudianteId(any(), any()))
                .thenReturn(Optional.empty());


        MockRestServiceServer server = MockRestServiceServer.createServer(restTemplate);
        server.expect(MockRestRequestMatchers.requestTo(Url.URL_GESTION_ACADEMICA +
                        "materias/" + materia.getId()))
                .andRespond(MockRestResponseCreators.withSuccess("{" +
                        "  \"id\": 1," +
                        "  \"nombre\": \"Calculo 1\"," +
                        "  \"descripcion\": \"Matematicas descripcion testttt\"," +
                        "  \"pensum\": {" +
                        "    \"id\": 2," +
                        "    \"anio\": 2022" +
                        "  }," +
                        "  \"materiaPrerequisito\": null" +
                        "}", MediaType.APPLICATION_JSON));

        NotFoundItemsInException exception = assertThrows(NotFoundItemsInException.class,
                () -> this.estudianteCursoCasoUso.registrarseEstudianteCurso(1L,
                        new Estudiante()));
        assertNotNull(exception);
        assertEquals(ERROR_MATERIA_NO_PENSUM, exception.getMessage());
    }


    @Test
    void cuandoSeRegistraUnEstudianteEnUnCursoYLaMateriaPerteneceAlPensum() throws JsonProcessingException {
        Pensum pensum = new Pensum();
        pensum.setId(1L);

        Estudiante estudiante = new Estudiante();
        estudiante.setId(1L);
        Materia materia = new Materia();
        materia.setId(1L);
        materia.setPensum(pensum);
        CursoEntity cursoEntity = new CursoEntity();
        cursoEntity.setId(1L);
        cursoEntity.setMateria(materia);
        cursoEntity.setEstado("En Curso");
        SemestreAcademicoEntity semestreAcademico = new SemestreAcademicoEntity();
        semestreAcademico.setId(1L);
        cursoEntity.setSemestreAcademicoEntity(semestreAcademico);
        Optional<CursoEntity> op = Optional.of(cursoEntity);
        EstudianteCursoEntity estudianteCursoEntity = new EstudianteCursoEntity();
        estudianteCursoEntity.setId(1L);
        estudianteCursoEntity.setEstudiante(estudiante);
        estudianteCursoEntity.setCurso(cursoEntity);
        when(this.cursoRepository.findById(any()))
                .thenReturn(op);
        when(this.estudiantePensumRepository
                .findByPensumIdAndEstudianteId(any(), any()))
                .thenReturn(Optional.of(new EstudiantePensumEntity()));
        when(this.estudianteCursoRepository.registrarCurso(any()))
                .thenReturn(estudianteCursoEntity);

        MockRestServiceServer server = MockRestServiceServer.createServer(restTemplate);
        server.expect(MockRestRequestMatchers.requestTo(Url.URL_GESTION_ACADEMICA +
                        "materias/" + materia.getId()))
                .andRespond(MockRestResponseCreators.withSuccess("{" +
                        "  \"id\": 1," +
                        "  \"nombre\": \"Calculo 1\"," +
                        "  \"descripcion\": \"Matematicas descripcion testttt\"," +
                        "  \"pensum\": {" +
                        "    \"id\": 1," +
                        "    \"anio\": 2022" +
                        "  }," +
                        "  \"materiaPrerequisito\": null" +
                        "}", MediaType.APPLICATION_JSON));

        EstudianteCurso estudianteCurso = this.estudianteCursoCasoUso.registrarseEstudianteCurso(1L,
                new Estudiante());
        assertNotNull(estudianteCurso);
    }


    @Test
    void cuandoSeRegistraUnEstudianteEnUnCursoYLaMateriaPerteneceAlPensumEstadoError() throws JsonProcessingException {
        Pensum pensum = new Pensum();
        pensum.setId(1L);

        Estudiante estudiante = new Estudiante();
        estudiante.setId(1L);
        Materia materia = new Materia();
        materia.setId(1L);
        materia.setPensum(pensum);
        CursoEntity cursoEntity = new CursoEntity();
        cursoEntity.setId(1L);
        cursoEntity.setMateria(materia);
        cursoEntity.setEstado("eeee");
        SemestreAcademicoEntity semestreAcademico = new SemestreAcademicoEntity();
        semestreAcademico.setId(1L);
        cursoEntity.setSemestreAcademicoEntity(semestreAcademico);
        Optional<CursoEntity> op = Optional.of(cursoEntity);
        EstudianteCursoEntity estudianteCursoEntity = new EstudianteCursoEntity();
        estudianteCursoEntity.setId(1L);
        estudianteCursoEntity.setEstudiante(estudiante);
        estudianteCursoEntity.setCurso(cursoEntity);
        when(this.cursoRepository.findById(any()))
                .thenReturn(op);
        when(this.estudiantePensumRepository
                .findByPensumIdAndEstudianteId(any(), any()))
                .thenReturn(Optional.of(new EstudiantePensumEntity()));
        when(this.estudianteCursoRepository.registrarCurso(any()))
                .thenReturn(estudianteCursoEntity);

        MockRestServiceServer server = MockRestServiceServer.createServer(restTemplate);
        server.expect(MockRestRequestMatchers.requestTo(Url.URL_GESTION_ACADEMICA +
                        "materias/" + materia.getId()))
                .andRespond(MockRestResponseCreators.withSuccess("{" +
                        "  \"id\": 1," +
                        "  \"nombre\": \"Calculo 1\"," +
                        "  \"descripcion\": \"Matematicas descripcion testttt\"," +
                        "  \"pensum\": {" +
                        "    \"id\": 1," +
                        "    \"anio\": 2022" +
                        "  }," +
                        "  \"materiaPrerequisito\": null" +
                        "}", MediaType.APPLICATION_JSON));

        NotFoundItemsInException exception = assertThrows(NotFoundItemsInException.class,
                () -> this.estudianteCursoCasoUso.registrarseEstudianteCurso(1L,
                        new Estudiante()));
        assertNotNull(exception);
        assertEquals(ERROR_CURSO_CERRADO, exception.getMessage());
    }


    @Test
//    @Disabled
    void cuandoSeRegistraUnEstudianteEnUnCursoYLaMateriaPerteneceAlPensumConPrerequisitoNoAprobado() throws JsonProcessingException {
        Pensum pensum = new Pensum();
        pensum.setId(1L);

        Estudiante estudiante = new Estudiante();
        estudiante.setId(1L);
        Materia materia = new Materia();
        materia.setId(1L);
        materia.setPensum(pensum);
        Materia materiaPrequisito = new Materia();
        materiaPrequisito.setId(2L);
        materia.setMateriaPrerequisito(materiaPrequisito);
        CursoEntity cursoEntity = new CursoEntity();
        cursoEntity.setId(1L);
        cursoEntity.setMateria(materia);
        cursoEntity.setEstado("En Curso");
        SemestreAcademicoEntity semestreAcademico = new SemestreAcademicoEntity();
        semestreAcademico.setId(1L);
        cursoEntity.setSemestreAcademicoEntity(semestreAcademico);
        cursoEntity.setMateria(materia);

        Optional<CursoEntity> op = Optional.of(cursoEntity);
        EstudianteCursoEntity estudianteCursoEntity = new EstudianteCursoEntity();
        estudianteCursoEntity.setId(1L);
        estudianteCursoEntity.setEstudiante(estudiante);
        estudianteCursoEntity.setCurso(cursoEntity);
        when(this.cursoRepository.findById(any()))
                .thenReturn(op);
        when(this.estudiantePensumRepository
                .findByPensumIdAndEstudianteId(any(), any()))
                .thenReturn(Optional.of(new EstudiantePensumEntity()));
        when(this.estudianteCursoRepository.registrarCurso(any()))
                .thenReturn(estudianteCursoEntity);

        when(this.estudianteCursoRepository.ListarCursosEstudiante(any()))
                .thenReturn(List.of(estudianteCursoEntity));


        when(this.cursoRepository.listCursos(any()))
                .thenReturn(List.of(cursoEntity));


        MockRestServiceServer server = MockRestServiceServer.createServer(restTemplate);
        server.expect(MockRestRequestMatchers.requestTo(Url.URL_GESTION_ACADEMICA +
                        "materias/" + materia.getId()))
                .andRespond(MockRestResponseCreators.withSuccess("{" +
                        "  \"id\": 1," +
                        "  \"nombre\": \"Calculo 1\"," +
                        "  \"descripcion\": \"Matematicas descripcion testttt\"," +
                        "  \"pensum\": {" +
                        "    \"id\": 1," +
                        "    \"anio\": 2022" +
                        "  }," +
                        "  \"materiaPrerequisito\": null" +
                        "}", MediaType.APPLICATION_JSON));

        NotFoundItemsInException exception = assertThrows(NotFoundItemsInException.class,
                () -> this.estudianteCursoCasoUso.registrarseEstudianteCurso(1L,
                        new Estudiante()));
        assertNotNull(exception);
        assertEquals(ERROR_PREREQUISITO_NO_APROBADO, exception.getMessage());
    }


    @Test
//    @Disabled
    void cuandoSeRegistraUnEstudianteEnUnCursoYLaMateriaPerteneceAlPensumConPrerequisitoAprobado() throws JsonProcessingException {
        Pensum pensum = new Pensum();
        pensum.setId(1L);

        Estudiante estudiante = new Estudiante();
        estudiante.setId(1L);
        Materia materia = new Materia();
        materia.setId(1L);
        materia.setPensum(pensum);
        Materia materiaPrequisito = new Materia();
        materiaPrequisito.setId(2L);
        materia.setMateriaPrerequisito(materiaPrequisito);
        CursoEntity cursoEntity = new CursoEntity();
        cursoEntity.setId(1L);
        cursoEntity.setMateria(materia);
        cursoEntity.setEstado("En Curso");
        SemestreAcademicoEntity semestreAcademico = new SemestreAcademicoEntity();
        semestreAcademico.setId(1L);
        cursoEntity.setSemestreAcademicoEntity(semestreAcademico);
//        cursoEntity.setMateria(materia);

        Optional<CursoEntity> op = Optional.of(cursoEntity);
        EstudianteCursoEntity estudianteCursoEntity = new EstudianteCursoEntity();
        estudianteCursoEntity.setId(1L);
        estudianteCursoEntity.setEstudiante(estudiante);
        estudianteCursoEntity.setCurso(cursoEntity);
        estudianteCursoEntity.setNotaFinal(3.9);
        when(this.cursoRepository.findById(any()))
                .thenReturn(op);
        when(this.estudiantePensumRepository
                .findByPensumIdAndEstudianteId(any(), any()))
                .thenReturn(Optional.of(new EstudiantePensumEntity()));
        when(this.estudianteCursoRepository.registrarCurso(any()))
                .thenReturn(estudianteCursoEntity);

        when(this.estudianteCursoRepository.ListarCursosEstudiante(any()))
                .thenReturn(List.of(estudianteCursoEntity));


        when(this.cursoRepository.listCursos(any()))
                .thenReturn(List.of(cursoEntity));


        MockRestServiceServer server = MockRestServiceServer.createServer(restTemplate);
        server.expect(MockRestRequestMatchers.requestTo(Url.URL_GESTION_ACADEMICA +
                        "materias/" + materia.getId()))
                .andRespond(MockRestResponseCreators.withSuccess("{" +
                        "  \"id\": 1," +
                        "  \"nombre\": \"Calculo 1\"," +
                        "  \"descripcion\": \"Matematicas descripcion testttt\"," +
                        "  \"pensum\": {" +
                        "    \"id\": 1," +
                        "    \"anio\": 2022" +
                        "  }," +
                        "  \"materiaPrerequisito\": null" +
                        "}", MediaType.APPLICATION_JSON));

        EstudianteCurso estudianteCurso = this.estudianteCursoCasoUso.registrarseEstudianteCurso(1L,
                new Estudiante());
        assertNotNull(estudianteCurso);
    }


    @Test
    void cuandoSelistanHorariosConUnUsuarioNoAutorizado() {

        Usuario usuario = new Usuario();
        usuario.setUsuarioId(1L);
        usuario.setTipoUsuario(2L);

        HttpServletRequest request = mock(HttpServletRequest.class);
//        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("usuario")).thenReturn(usuario);


        NotLoggedInException exception = assertThrows(NotLoggedInException.class,
                () -> this.estudianteCursoCasoUso.listaHorario(session));
        assertNotNull(exception);
        assertEquals(ERROR_NO_AUTORIZADO, exception.getMessage());

    }

    @Test
    void cuandoSelistanHorariosConUnUsuarioAutorizado() {

        CursoEntity cursoEntity = new CursoEntity();
        cursoEntity.setId(1L);
        cursoEntity.setEstado("En Curso");

        Estudiante estudiante = new Estudiante();
        estudiante.setNombre("estudiante");

        Usuario usuario = new Usuario();
        usuario.setUsuarioId(1L);
        usuario.setTipoUsuario(4L);

        HttpServletRequest request = mock(HttpServletRequest.class);
//        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("usuario")).thenReturn(usuario);

        EstudianteCursoEntity estudianteCursoEntity = new EstudianteCursoEntity();
        estudianteCursoEntity.setId(1L);
        estudianteCursoEntity.setEstudiante(estudiante);
        estudianteCursoEntity.setNotaFinal(3.9);
        estudianteCursoEntity.setCurso(cursoEntity);


        when(this.estudianteCursoRepository.ListarCursosEstudiante(any()))
                .thenReturn(List.of(estudianteCursoEntity));


        List<String> horario = this.estudianteCursoCasoUso.listaHorario(session);
        assertNotNull(horario);
    }

    @Test
    void cuandoElEstudianteNoEstaMatriculadoEnSubirNota() {
        EstudianteCurso estudianteCurso = new EstudianteCurso();
        estudianteCurso.setId(1L);
        when(this.estudianteCursoRepository.findByEstudianteCursoEntityId(any()))
                .thenReturn(Optional.empty());

        NotFoundItemsInException exception = assertThrows(NotFoundItemsInException.class,
                () -> this.estudianteCursoCasoUso.subirNota(estudianteCurso, new Profesor()));

        assertNotNull(exception);
        assertEquals(ERROR_ESTUDIANTE_NO_MATRICULADO, exception.getMessage());
    }

    @Test
    void cuandoElEstudianteEstaMatriculadoEnSubirNotaYSeSubeNotaDelPrevio() {
        EstudianteCurso estudianteCurso = new EstudianteCurso();
        estudianteCurso.setId(1L);
        estudianteCurso.setPrevio3(4D);
        when(this.estudianteCursoRepository.findByEstudianteCursoEntityId(any()))
                .thenReturn(Optional.of(new EstudianteCursoEntity()));

        NotCreatedInException exception = assertThrows(NotCreatedInException.class,
                () -> this.estudianteCursoCasoUso.subirNota(estudianteCurso, new Profesor()));

        assertNotNull(exception);
        assertEquals(ERROR_PREVIO_3, exception.getMessage());
    }

    @Test
    void cuandoElEstudianteEstaMatriculadoEnSubirNotaYSeSubeNotaDelPrevioOtroPrefesorDelCurso() {
        EstudianteCurso estudianteCurso = new EstudianteCurso();
        estudianteCurso.setId(1L);
//        estudianteCurso.setPrevio3(4D);
        EstudianteCursoEntity estudianteCursoEntity = new EstudianteCursoEntity();
        estudianteCursoEntity.setId(1L);
        CursoEntity cursoEntity = new CursoEntity();
        Profesor profesor = new Profesor();
        profesor.setId(1L);
        cursoEntity.setProfesor(profesor);
        estudianteCursoEntity.setCurso(cursoEntity);
        when(this.estudianteCursoRepository.findByEstudianteCursoEntityId(any()))
                .thenReturn(Optional.of(estudianteCursoEntity));
        Profesor profesorDiferente = new Profesor();
        profesorDiferente.setId(2L);
        NotCreatedInException exception = assertThrows(NotCreatedInException.class,
                () -> this.estudianteCursoCasoUso.subirNota(estudianteCurso, profesorDiferente));

        assertNotNull(exception);
        assertEquals(ERROR_PROFESOR_CURSO, exception.getMessage());
    }


    @Test
    void cuandoSeQuiereSubirNotaYAlgunPrevioYaTieneNota() {
        EstudianteCurso estudianteCurso = new EstudianteCurso();
        estudianteCurso.setId(1L);
        estudianteCurso.setPrevio1(4D);
        EstudianteCursoEntity estudianteCursoEntity = new EstudianteCursoEntity();
        estudianteCursoEntity.setId(1L);
        estudianteCursoEntity.setPrevio1(4D);
        CursoEntity cursoEntity = new CursoEntity();
        Profesor profesor = new Profesor();
        profesor.setId(1L);
        cursoEntity.setProfesor(profesor);
        estudianteCursoEntity.setCurso(cursoEntity);
        when(this.estudianteCursoRepository.findByEstudianteCursoEntityId(any()))
                .thenReturn(Optional.of(estudianteCursoEntity));
        NotCreatedInException exception = assertThrows(NotCreatedInException.class,
                () -> this.estudianteCursoCasoUso.subirNota(estudianteCurso, profesor));

        assertNotNull(exception);
        assertEquals(ERROR_PREVIOS, exception.getMessage());
    }

    @Test
    void cuandoSeQuiereSubirNotaYLosPrevioEstanVacios() {
        EstudianteCurso estudianteCurso = new EstudianteCurso();
        estudianteCurso.setId(1L);
        estudianteCurso.setPrevio1(3D);
        estudianteCurso.setPrevio2(3D);
        estudianteCurso.setPrevio4(3D);
//        estudianteCurso.setEstudiante().
        SemestreAcademicoEntity semestreAcademico = new SemestreAcademicoEntity();
        semestreAcademico.setId(1L);

        EstudianteCursoEntity estudianteCursoEntity = new EstudianteCursoEntity();

        estudianteCursoEntity.setId(1L);
//        estudianteCursoEntity.se
        CursoEntity cursoEntity = new CursoEntity();
        cursoEntity.setSemestreAcademicoEntity(semestreAcademico);
        Profesor profesor = new Profesor();
        profesor.setId(1L);
        cursoEntity.setProfesor(profesor);
        estudianteCursoEntity.setCurso(cursoEntity);
        when(this.estudianteCursoRepository.findByEstudianteCursoEntityId(any()))
                .thenReturn(Optional.of(estudianteCursoEntity));
        when(this.estudianteCursoRepository.actualizarCursoEstudiante(any()))
                .thenReturn(estudianteCursoEntity);


        EstudianteCurso estudianteCurso1 = this.estudianteCursoCasoUso.subirNota(estudianteCurso, profesor);

        assertNotNull(estudianteCurso1);
    }
}
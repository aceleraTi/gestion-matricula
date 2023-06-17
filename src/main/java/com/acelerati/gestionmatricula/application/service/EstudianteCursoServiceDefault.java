package com.acelerati.gestionmatricula.application.service;

import com.acelerati.gestionmatricula.application.service.interfaces.EstudianteCursoService;
import com.acelerati.gestionmatricula.domain.model.*;
import com.acelerati.gestionmatricula.domain.model.repository.CursoRepository;
import com.acelerati.gestionmatricula.domain.model.repository.EstudianteCursoRepository;
import com.acelerati.gestionmatricula.domain.model.repository.EstudiantePensumRepository;
import com.acelerati.gestionmatricula.domain.model.repository.HorarioRepository;
import com.acelerati.gestionmatricula.infraestructure.entitys.CursoEntity;
import com.acelerati.gestionmatricula.infraestructure.entitys.EstudianteCursoEntity;
import com.acelerati.gestionmatricula.infraestructure.entitys.HorarioEntity;
import com.acelerati.gestionmatricula.infraestructure.exceptions.NotCreatedInException;
import com.acelerati.gestionmatricula.infraestructure.exceptions.NotFoundItemsInException;
import com.acelerati.gestionmatricula.infraestructure.rest.mappers.EstudianteCursoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

import static com.acelerati.gestionmatricula.domain.util.Validaciones.*;
import static com.acelerati.gestionmatricula.infraestructure.rest.mappers.CursoMapper.alCursoEntity;
import static com.acelerati.gestionmatricula.infraestructure.rest.mappers.EstudianteCursoMapper.alEstudianteCurso;
import static com.acelerati.gestionmatricula.infraestructure.settings.Url.URL_GESTION_ACADEMICA;

@Service
public class EstudianteCursoServiceDefault implements EstudianteCursoService {
    private  final EstudianteCursoRepository estudianteCursoRepository;
    private final CursoRepository cursoRepository;
    private final EstudiantePensumRepository estudiantePensumRepository;
    private final HorarioRepository horarioRepository;
    @Autowired
    private RestTemplate restTemplate;

    public EstudianteCursoServiceDefault(EstudianteCursoRepository estudianteCursoRepository, CursoRepository cursoRepository, EstudiantePensumRepository estudiantePensumRepository, HorarioRepository horarioRepository) {
        this.estudianteCursoRepository = estudianteCursoRepository;
        this.cursoRepository = cursoRepository;
        this.estudiantePensumRepository = estudiantePensumRepository;
        this.horarioRepository = horarioRepository;
    }

  /*  @Override
    public EstudianteCurso registrarCurso(EstudianteCurso estudianteCurso) {
        EstudianteCursoEntity estudianteCursoEntity=alEstudianteCursoEntity(estudianteCurso);
        return alEstudianteCurso(estudianteCursoRepository.registrarCurso(estudianteCursoEntity));
    }*/

 /*   @Override
    public EstudianteCurso actualizarCursoEstudiante(EstudianteCurso estudianteCurso) {
        EstudianteCursoEntity estudianteCursoEntity=alEstudianteCursoEntity(estudianteCurso);
        return alEstudianteCurso(estudianteCursoRepository.actualizarCursoEstudiante(estudianteCursoEntity));
    }
*/


  /*  @Override
    public List<EstudianteCurso> listarEstudianteCursos(Estudiante estudiante) {
        List<EstudianteCursoEntity>estudianteCursoEntities=estudianteCursoRepository.ListarCursosEstudiante(estudiante);
        return estudianteCursoEntities.stream()
                .map(EstudianteCursoMapper::alEstudianteCurso)
                .collect(Collectors.toList());
    }
*/


    @Override
    public List<EstudianteCurso> findByCurso(Curso curso) {

        List<EstudianteCursoEntity>estudianteCursoEntities=estudianteCursoRepository.findByCurso(alCursoEntity(curso));
        return estudianteCursoEntities.stream()
                .map(EstudianteCursoMapper::alEstudianteCurso)
                .collect(Collectors.toList());
    }

  /*  @Override
    public List<EstudianteCurso> guardarEstudiantesCursos(List<EstudianteCurso> estudianteCurso) {

        List<EstudianteCursoEntity>estudianteCursoEntities=estudianteCursoRepository.guardarEstudiantesCursos(
                estudianteCurso.stream()
                        .map(EstudianteCursoMapper::alEstudianteCursoEntity)
                        .collect(Collectors.toList()));

        return estudianteCursoEntities.stream()
                .map(EstudianteCursoMapper::alEstudianteCurso)
                .collect(Collectors.toList());
    }*/

    @Override
    public EstudianteCurso registrarseEstudianteCurso(Long idCurso, HttpSession session) {

        Usuario usuario=(Usuario) session.getAttribute("usuario");
        Estudiante estudiante= validarEstudiante(validarLogged(4L,usuario));
        CursoEntity cursoEntity=cursoRepository.findById(idCurso);
        Materia materia=new Materia();
        try{
            Materia materia1=restTemplate.getForObject(URL_GESTION_ACADEMICA+"/materias/"+cursoEntity.getMateria().getId(),
                    Materia.class);
            materia=materia1;

        }catch (HttpServerErrorException exception){
            throw new NotFoundItemsInException("Materia no encontrada");
        }catch (HttpClientErrorException exception){
        }
        try {
            assert materia != null;
            if(Boolean.FALSE.equals(estudiantePensumRepository
                    .findByIdPensum(materia.getPensum().getId()))){
                throw new NotFoundItemsInException("No estas matriculado en el programa academico de esta materia");
            }
        } catch (AssertionError e) {
            throw new NotFoundItemsInException("La materia no existe");
        }
        if(materia.getMateriaPrerequisito()!=null){
            List<EstudianteCursoEntity> estudianteCursoEntityList=estudianteCursoRepository.ListarCursosEstudiante(estudiante)
                    .stream().filter(estudianteCursoEntity -> estudianteCursoEntity.getNotaFinal()!=null)
                    .collect(Collectors.toList());
            List<CursoEntity> listCursoEntityList= cursoRepository.listCursos(materia.getMateriaPrerequisito());
         /*   List<EstudianteCurso> estudianteCursosPre=estudianteCursoList.stream()
                    .filter(estudianteCurso -> listCurso.stream()
                            .anyMatch(curso1 -> curso1.getId()==estudianteCurso.getCurso().getId()))
                    .filter(estudianteCurso -> estudianteCurso.getNotaFinal()>3.5)
                    .collect(Collectors.toList());*/

            if(!estudianteCursoEntityList.stream().anyMatch(estudianteCurso -> listCursoEntityList.stream()
                    .anyMatch(curso1 -> curso1.getId() == estudianteCurso.getCurso().getId()
                            && estudianteCurso.getNotaFinal() > 3.5))){
                throw new NotFoundItemsInException("Aun no has aprobado el prerrequisito de esta materia");
            }
        }
        if(!cursoEntity.getEstado().equalsIgnoreCase("En Curso")){
            throw new NotFoundItemsInException("El curso esta cerrado");
        }
        EstudianteCursoEntity estudianteCursoEntity=new EstudianteCursoEntity();
        estudianteCursoEntity.setEstudiante(estudiante);
        estudianteCursoEntity.setCurso(cursoEntity);
        return alEstudianteCurso(estudianteCursoRepository.registrarCurso(estudianteCursoEntity));

    }

    @Override
    public List<String> listaHorario(HttpSession session) {

        Estudiante estudiante= validarEstudiante(validarLogged(4L,(Usuario) session.getAttribute("usuario")));

        List<CursoEntity> cursoEntityList=listarCursos(estudiante);

        List<List<HorarioEntity>> horarioList = cursoEntityList.stream()
                .map(horarioRepository::findByCursoEntity)
                .collect(Collectors.toList());

       return horarioList.stream()
                .flatMap(List::stream)
                .map(horario -> "Curso "+ horario.getCurso().getId() + " - Dia " + horario.getDia() + " - Hora " + horario.getHoraInicio())
                .collect(Collectors.toList());
    }

    @Override
    public EstudianteCurso subirNota(EstudianteCurso estudianteCurso, HttpSession session) {

        Usuario usuario=(Usuario) session.getAttribute("usuario");
        Profesor profesor= validarProfesor(validarLogged(3L,usuario));
        if(estudianteCurso.getPrevio3()!=null){
            throw new NotCreatedInException("El previo 3 corresponde a la sumatoria de las tareas");
        }
        EstudianteCursoEntity estudianteCursoEntity=estudianteCursoRepository
                .findByEstudianteCursoEntityId(estudianteCurso.getId());


        if(profesor.getId()!=estudianteCursoEntity.getCurso().getProfesor().getId()){
            throw new NotCreatedInException("Este Curso no esta asignado a usted");
        }
        if(estudianteCurso.getPrevio1()!=null && estudianteCursoEntity.getPrevio1()!=null ||
                estudianteCurso.getPrevio2()!=null && estudianteCursoEntity.getPrevio2()!=null ||
                estudianteCurso.getPrevio4()!=null && estudianteCursoEntity.getPrevio4()!=null ){
            throw new NotCreatedInException("Uno de los previos ya tiene  nota asignada");
        }
        if(estudianteCurso.getPrevio1()!=null)
            estudianteCursoEntity.setPrevio1(estudianteCurso.getPrevio1());
        if(estudianteCurso.getPrevio2()!=null)
            estudianteCursoEntity.setPrevio2(estudianteCurso.getPrevio2());
        if(estudianteCurso.getPrevio4()!=null)
            estudianteCursoEntity.setPrevio4(estudianteCurso.getPrevio4());
        return alEstudianteCurso(estudianteCursoRepository.actualizarCursoEstudiante(estudianteCursoEntity));

    }
  /*  @Override
    public EstudianteCurso findByEstudianteCursoId(Long id) {

        return  alEstudianteCurso(estudianteCursoRepository.findByEstudianteCursoEntityId(id));
    }
*/
    private List<CursoEntity> listarCursos(Estudiante estudiante) {
         return estudianteCursoRepository.ListarCursosEstudiante(estudiante)
                .stream().map(EstudianteCursoEntity::getCurso)
                .filter(p -> p.getEstado().equalsIgnoreCase("En Curso"))
                .collect(Collectors.toList());
    }

}

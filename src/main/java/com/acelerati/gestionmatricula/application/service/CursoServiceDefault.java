package com.acelerati.gestionmatricula.application.service;

import com.acelerati.gestionmatricula.application.service.interfaces.CursoService;
import com.acelerati.gestionmatricula.domain.model.*;
import com.acelerati.gestionmatricula.domain.model.repository.CursoRepository;
import com.acelerati.gestionmatricula.domain.model.repository.EstudianteCursoRepository;
import com.acelerati.gestionmatricula.domain.model.repository.EstudianteCursoTareaRepository;
import com.acelerati.gestionmatricula.domain.model.repository.TareaRepository;
import com.acelerati.gestionmatricula.infraestructure.entitys.CursoEntity;
import com.acelerati.gestionmatricula.infraestructure.entitys.EstudianteCursoEntity;
import com.acelerati.gestionmatricula.infraestructure.exceptions.NotCreatedInException;
import com.acelerati.gestionmatricula.infraestructure.exceptions.NotLoggedInException;
import com.acelerati.gestionmatricula.infraestructure.rest.mappers.CursoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

import static com.acelerati.gestionmatricula.domain.util.Validaciones.validarLogged;
import static com.acelerati.gestionmatricula.domain.util.Validaciones.validarProfesor;
import static com.acelerati.gestionmatricula.infraestructure.rest.mappers.CursoMapper.alCurso;
import static com.acelerati.gestionmatricula.infraestructure.rest.mappers.CursoMapper.alCursoEntity;
import static com.acelerati.gestionmatricula.infraestructure.settings.Url.URL_GESTION_USUARIO;

@Service
public class CursoServiceDefault implements CursoService {

    private final CursoRepository cursoRepository;
    private final EstudianteCursoRepository estudianteCursoRepository;
    private final EstudianteCursoTareaRepository estudianteCursoTareaRepository;
    private final TareaRepository tareaRepository;
    @Autowired
    private RestTemplate restTemplate;

    public CursoServiceDefault(CursoRepository cursoRepository, EstudianteCursoRepository estudianteCursoRepository, RestTemplate restTemplate, EstudianteCursoTareaRepository estudianteCursoTareaRepository, TareaRepository tareaRepository) {
        this.cursoRepository = cursoRepository;
        this.estudianteCursoRepository = estudianteCursoRepository;
        this.estudianteCursoTareaRepository = estudianteCursoTareaRepository;
        this.tareaRepository = tareaRepository;
    }

    @Override
    public Curso create(Curso curso, HttpSession session ) {

        validarLogged(2L, (Usuario) session.getAttribute("usuario"));

        CursoEntity cursoEntity=alCursoEntity(curso);
        if(cursoRepository.esGrupoUnicoMateriaSemetre(cursoEntity.getGrupo(),
                cursoEntity.getMateria(),cursoEntity.getSemestreAcademicoEntity())){

            if(cursoRepository.countProfesorCurso(cursoEntity)){
                return alCurso(cursoRepository.save(cursoEntity)) ;
            }
            throw new NotCreatedInException("El profesor ya tiene los 4 cursos permitidos en curso");
        }
        else{
            throw new NotCreatedInException("El grupo para el semestre y materia ya existe");
        }
    }


    @Override
    public Curso asignarProfesor(Long idCurso, Long idProfesor , HttpSession session) {

        Usuario usuario = (Usuario) session.getAttribute("usuario");
        validarLogged(2L, usuario);
        try {

            Usuario validarUsuario = restTemplate.getForObject(URL_GESTION_USUARIO + "/api/1.0/usuarios/" + idProfesor, Usuario.class);
            if (validarUsuario.getTipoUsuario() != 3) {
                throw new NotLoggedInException("Este usuario no tiene rol de profesor");
            }
            Profesor profesor = new Profesor();
            profesor.setId(idProfesor);
            CursoEntity cursoEntity = cursoRepository.findById(idCurso);
            cursoEntity.setProfesor(profesor);
            if(!cursoRepository.countProfesorCurso(cursoEntity) ||cursoEntity.getEstado().equalsIgnoreCase("Cerrado")){
                throw new NotCreatedInException("El profesor ya tiene los 4 cursos permitidos en curso");
            }
             return alCurso(cursoRepository.update(cursoEntity));
        } catch (HttpServerErrorException exception) {
            throw new NotLoggedInException("Usuario no existe");
        }
    }

    @Override
    public Curso cerrarCurso(Long idCurso, HttpSession session){

            Usuario usuario=(Usuario) session.getAttribute("usuario");
            Profesor profesor = validarProfesor(validarLogged(3L, usuario));

            CursoEntity cursoEntity = cursoRepository.findById(idCurso);
            if (cursoEntity.getProfesor().getId() != profesor.getId()) {
                throw new NotLoggedInException("No tiene permisos para cerrar el curso");
            }
            List<EstudianteCursoEntity> estudianteCursosCursoEntities = estudianteCursoRepository.findByCurso(cursoEntity);
            for (EstudianteCursoEntity estud : estudianteCursosCursoEntities) {
                if (estud.getPrevio1() == null ||
                        estud.getPrevio2() == null ||
                        estud.getPrevio4() == null) {
                    throw new NotCreatedInException("Aun no se han subido todas las notas de los previos");
                } else if (estud.getNotaFinal() != null) {
                    continue;
                }
                Double notaPrevio3 = tareaRepository.findByCursoId(idCurso)
                        .stream()
                        .mapToDouble(tar -> estudianteCursoTareaRepository.notaTarea(tar.getId(), estud.getId()))
                        .average()
                        .orElse(0.0);

                estud.setPrevio3(notaPrevio3);

                Double notaFinal = ((estud.getPrevio1() + estud.getPrevio2() +
                        estud.getPrevio3()) * 7/30) + (estud.getPrevio4() * 0.3);

                estud.setNotaFinal(notaFinal);
            }
            List<EstudianteCursoEntity> estudianteCursosCerrados =  estudianteCursoRepository.guardarEstudiantesCursos(estudianteCursosCursoEntities);
            cursoEntity.setEstado("Cerrado");
            return alCurso(cursoRepository.update(cursoEntity));
        }

    @Override
    public Page<Curso> findByProfesor(Profesor profesor, Pageable pageable) {

        Page<CursoEntity> cursoEntityPage=cursoRepository.findByProfesor(profesor,pageable);
        List<Curso> cursoList=cursoEntityPage.getContent()
                .stream()
                .map(CursoMapper::alCurso)
                .collect(Collectors.toList());
        return new PageImpl<>(cursoList,cursoEntityPage.getPageable(),cursoEntityPage.getTotalElements());
    }

  /*  @Override
    public List<Curso> listCursos(Materia materia) {
        return cursoRepository.listCursos(materia).stream()
                .map(CursoMapper::alCurso)
                .collect(Collectors.toList());
    }*/

    @Override
    public Curso findById(Long id) {
        return alCurso(cursoRepository.findById(id));
    }




}

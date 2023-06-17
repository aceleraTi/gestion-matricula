package com.acelerati.gestionmatricula.application.service;

import com.acelerati.gestionmatricula.application.service.interfaces.HorarioService;
import com.acelerati.gestionmatricula.domain.model.Curso;
import com.acelerati.gestionmatricula.domain.model.Horario;
import com.acelerati.gestionmatricula.domain.model.Usuario;
import com.acelerati.gestionmatricula.domain.model.repository.CursoRepository;
import com.acelerati.gestionmatricula.domain.model.repository.HorarioRepository;
import com.acelerati.gestionmatricula.infraestructure.entitys.CursoEntity;
import com.acelerati.gestionmatricula.infraestructure.entitys.HorarioEntity;
import com.acelerati.gestionmatricula.infraestructure.rest.mappers.HorarioMapper;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

import static com.acelerati.gestionmatricula.domain.util.Validaciones.validarLogged;
import static com.acelerati.gestionmatricula.infraestructure.rest.mappers.CursoMapper.alCursoEntity;
import static com.acelerati.gestionmatricula.infraestructure.rest.mappers.HorarioMapper.alHorario;
import static com.acelerati.gestionmatricula.infraestructure.rest.mappers.HorarioMapper.alHorarioEntity;

@Service
public class HorarioServiceImplement implements HorarioService {
    private final HorarioRepository horarioRepository;
    private final CursoRepository cursoRepository;

    public HorarioServiceImplement(HorarioRepository horarioRepository, CursoRepository cursoRepository) {
        this.horarioRepository = horarioRepository;
        this.cursoRepository = cursoRepository;
    }

    /**
     * Este método se encarga de asignar un horario a un curso específico.
     * Recibe un objeto Horario y la sesión actual (HttpSession).     *
     * En primer lugar, se valida que esté autenticado como un usuario con ID 2,
     * utilizando el método validarLogged(2L, usuario).
     *
     * A continuación, se obtiene la entidad del curso asociado al horario mediante
     * el repositorio cursoRepository y el método findById(horario.getCurso().getId()),
     * pasando el ID del curso del objeto Horario.
     *
     * Se crea una nueva entidad HorarioEntity a partir del objeto Horario utilizando
     * el método alHorarioEntity(horario).
     *
     * Se establece la entidad del curso obtenida en el paso anterior en la
     * entidad del horario utilizando horarioEntity.setCurso(cursoEntity).
     *
     * Finalmente, se guarda el horario asignado en el repositorio utilizando horarioRepository.
     * asignarHorario(horarioEntity), y se devuelve el horario asignado como resultado tras convertirlo
     * a un objeto Horario utilizando el método alHorario.
     * @param horario
     * @param session
     * @return
     */
    @Override
    public Horario asignarHorario(Horario horario, HttpSession session) {
        validarLogged(2L,(Usuario) session.getAttribute("usuario"));
        CursoEntity cursoEntity=cursoRepository.findById(horario.getCurso().getId());

        HorarioEntity horarioEntity=alHorarioEntity(horario);
        horarioEntity.setCurso(cursoEntity);
        return alHorario(horarioRepository.asignarHorario(horarioEntity));
    }

    /**
     *  Este método busca los horarios asociados a un curso específico.
     *  Recibe un objeto Curso y devuelve una lista de objetos Horario.
     *
     * Se convierte el objeto Curso en su correspondiente entidad CursoEntity utilizando el método alCursoEntity(curso).
     *
     * Se utiliza el repositorio horarioRepository y el método findByCursoEntity(cursoEntity)
     * para obtener la lista de entidades HorarioEntity asociadas al curso.
     *
     * Luego, se realiza un mapeo de cada entidad HorarioEntity a su
     * correspondiente objeto Horario utilizando el método alHorario de la clase HorarioMapper.
     *
     * Finalmente, se recopilan los objetos Horario mapeados en una lista utilizando collect y se devuelve como resultado.
     * @param curso
     * @return
     */
    @Override
    public List<Horario> findByCurso(Curso curso) {
        CursoEntity cursoEntity=alCursoEntity(curso);
         return horarioRepository.findByCursoEntity(cursoEntity).stream()
                .map(HorarioMapper::alHorario)
                .collect(Collectors.toList());

    }
}

package com.acelerati.gestionmatricula.application.service;

import com.acelerati.gestionmatricula.application.service.interfaces.EstudiantePensumService;
import com.acelerati.gestionmatricula.domain.model.EstudiantePensum;
import com.acelerati.gestionmatricula.domain.model.Materia;
import com.acelerati.gestionmatricula.domain.model.Usuario;
import com.acelerati.gestionmatricula.domain.model.repository.CursoRepository;
import com.acelerati.gestionmatricula.domain.model.repository.EstudianteCursoRepository;
import com.acelerati.gestionmatricula.domain.model.repository.EstudiantePensumRepository;
import com.acelerati.gestionmatricula.infraestructure.entitys.CursoEntity;
import com.acelerati.gestionmatricula.infraestructure.entitys.EstudianteCursoEntity;
import com.acelerati.gestionmatricula.infraestructure.entitys.EstudiantePensumEntity;
import com.acelerati.gestionmatricula.infraestructure.exceptions.NotFoundItemsInException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.acelerati.gestionmatricula.domain.util.Validaciones.validarEstudiante;
import static com.acelerati.gestionmatricula.domain.util.Validaciones.validarLogged;
import static com.acelerati.gestionmatricula.infraestructure.rest.mappers.EstudiantePensumMapper.alEstudiantePensum;
import static com.acelerati.gestionmatricula.infraestructure.rest.mappers.EstudiantePensumMapper.alEstudiantePensumEntity;
import static com.acelerati.gestionmatricula.infraestructure.settings.Url.URL_GESTION_ACADEMICA;

@Service
public class EstudiantePensumServiceDefault implements EstudiantePensumService {

    private final EstudiantePensumRepository estudiantePensumRepository;
    private final CursoRepository cursoRepository;
    private final EstudianteCursoRepository estudianteCursoRepository;
    @Autowired
    private RestTemplate restTemplate;
    public EstudiantePensumServiceDefault(EstudiantePensumRepository estudiantePensumRepository, CursoRepository cursoRepository, EstudianteCursoRepository estudianteCursoRepository) {
        this.estudiantePensumRepository = estudiantePensumRepository;
        this.cursoRepository = cursoRepository;
        this.estudianteCursoRepository = estudianteCursoRepository;
    }

    @Override
    public EstudiantePensum registrar(EstudiantePensum estudiantePensum,HttpSession session) {
        validarEstudiante(validarLogged(4L,(Usuario) session.getAttribute("usuario")));
        EstudiantePensumEntity estudiantePensumEntity=alEstudiantePensumEntity(estudiantePensum);
        return alEstudiantePensum(estudiantePensumRepository.registrar(estudiantePensumEntity));
    }

  /*  @Override
    public Boolean findByIdPensum(Long idPensum) {
        return estudiantePensumRepository.findByIdPensum(idPensum);
    }
*/
    @Override
    public List<Materia> materiaList(Long idPensum, HttpSession session){
        Usuario usuario=(Usuario) session.getAttribute("usuario");
        validarEstudiante(validarLogged(4L, usuario));

        if(estudiantePensumRepository.findByPensumIdAndEstudianteId(idPensum,usuario.getUsuarioId())){
            List<Materia> materiasReturn=new ArrayList<>();
            ResponseEntity<List<Materia>> response = restTemplate.exchange(URL_GESTION_ACADEMICA + "/materias/pensum/" + idPensum,
                    HttpMethod.GET,null,new ParameterizedTypeReference<List<Materia>>() {});
            List<Materia> materias = response.getBody();
            for (Materia materia: materias) {
                List<CursoEntity> cursoEntityList=cursoRepository.listCursos(materia)
                        .stream().filter(p->p.getEstado().equalsIgnoreCase("Cerrado"))
                        .collect(Collectors.toList());
                for (CursoEntity cur: cursoEntityList) {
                  EstudianteCursoEntity estudianteCursoEntity=estudianteCursoRepository.findByEstudianteIdAndCursoId(usuario.getUsuarioId(),cur.getId());
                    if(estudianteCursoEntity!=null){
                        materiasReturn.add(materia);
                    }
                }
            }
            return  materiasReturn;
        }
        throw new NotFoundItemsInException("No esta matriculado en este pensum");
    }

}

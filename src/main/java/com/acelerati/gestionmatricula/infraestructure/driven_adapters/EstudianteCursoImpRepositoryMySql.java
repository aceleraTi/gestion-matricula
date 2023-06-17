package com.acelerati.gestionmatricula.infraestructure.driven_adapters;

import com.acelerati.gestionmatricula.domain.model.Estudiante;
import com.acelerati.gestionmatricula.domain.model.repository.EstudianteCursoRepository;
import com.acelerati.gestionmatricula.infraestructure.driven_adapters.interfaces.EstudianteCursoRepositoryMySql;
import com.acelerati.gestionmatricula.infraestructure.entitys.CursoEntity;
import com.acelerati.gestionmatricula.infraestructure.entitys.EstudianteCursoEntity;
import com.acelerati.gestionmatricula.infraestructure.exceptions.NotCreatedInException;
import com.acelerati.gestionmatricula.infraestructure.exceptions.NotFoundItemsInException;

import java.util.List;
import java.util.Optional;

public class EstudianteCursoImpRepositoryMySql implements EstudianteCursoRepository {

    private final EstudianteCursoRepositoryMySql estudianteCursoRepositoryMysql;


    public EstudianteCursoImpRepositoryMySql(EstudianteCursoRepositoryMySql estudianteCursoRepositoryMysql) {
        this.estudianteCursoRepositoryMysql = estudianteCursoRepositoryMysql;


    }

    @Override
    public EstudianteCursoEntity registrarCurso(EstudianteCursoEntity estudianteCursoEntity) {

      if(estudianteCursoRepositoryMysql.countByCursoAndEstudiante
              (estudianteCursoEntity.getCurso(),estudianteCursoEntity.getEstudiante())>0){
          throw new NotCreatedInException("Ya estas matriculado en el curso");
      }
        return estudianteCursoRepositoryMysql.save(estudianteCursoEntity);
    }

    @Override
    public List<EstudianteCursoEntity> ListarCursosEstudiante(Estudiante estudiante){

        return estudianteCursoRepositoryMysql.findByEstudiante(estudiante);
    }

    @Override
    public EstudianteCursoEntity findByEstudianteCursoEntityId(Long id) {
        Optional<EstudianteCursoEntity> optionalEstudianteCursoEntity=estudianteCursoRepositoryMysql.findById(id);
        if (!optionalEstudianteCursoEntity.isPresent()){
            throw new NotFoundItemsInException("El id del estudiante curso no existe existe.");
        }
        return optionalEstudianteCursoEntity.get();
    }

    @Override
    public EstudianteCursoEntity actualizarCursoEstudiante(EstudianteCursoEntity estudianteCursoEntity) {
        return estudianteCursoRepositoryMysql.save(estudianteCursoEntity);
    }

    @Override
    public List<EstudianteCursoEntity> findByCurso(CursoEntity cursoEntity) {
        return estudianteCursoRepositoryMysql.findByCurso(cursoEntity);
    }

    @Override
    public List<EstudianteCursoEntity> guardarEstudiantesCursos(List<EstudianteCursoEntity> estudianteCursoEntities) {
        return (List<EstudianteCursoEntity>) estudianteCursoRepositoryMysql.saveAll(estudianteCursoEntities);
    }

    @Override
    public EstudianteCursoEntity findByEstudianteIdAndCursoId(Long idEstudiante, Long idCurso) {
        Optional<EstudianteCursoEntity> optionalEstudianteCursoEntity=estudianteCursoRepositoryMysql
                .findByEstudianteIdAndCursoId(idEstudiante,idCurso);
        if (optionalEstudianteCursoEntity.isPresent()){
            return  optionalEstudianteCursoEntity.get();
        }
        return null;
    }


}

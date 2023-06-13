package com.acelerati.gestionmatricula.infraestructure.adapters;

import com.acelerati.gestionmatricula.domain.model.Estudiante;
import com.acelerati.gestionmatricula.domain.model.EstudiantePensum;
import com.acelerati.gestionmatricula.domain.model.Materia;
import com.acelerati.gestionmatricula.domain.persistence.EstudianteCursoRepository;
import com.acelerati.gestionmatricula.infraestructure.adapters.interfaces.CursoRepositoryMySql;
import com.acelerati.gestionmatricula.infraestructure.adapters.interfaces.EstudianteCursoRepositoryMySql;
import com.acelerati.gestionmatricula.infraestructure.adapters.interfaces.EstudiantePensumRepositoryMySql;
import com.acelerati.gestionmatricula.infraestructure.entitys.CursoEntity;
import com.acelerati.gestionmatricula.infraestructure.entitys.EstudianteCursoEntity;
import com.acelerati.gestionmatricula.infraestructure.entitys.EstudiantePensumEntity;
import com.acelerati.gestionmatricula.infraestructure.exceptions.NotCreatedInException;
import com.acelerati.gestionmatricula.infraestructure.exceptions.NotFoundItemsInException;

import java.util.List;
import java.util.Optional;

public class EstudianteCursoImpRepositoryMySql implements EstudianteCursoRepository {

    private final EstudianteCursoRepositoryMySql estudianteCursoRepositoryMysql;
    private final EstudiantePensumRepositoryMySql estudiantePensumRepositoryMySql;
    private final CursoRepositoryMySql cursoRepositoryMySql;

    public EstudianteCursoImpRepositoryMySql(EstudianteCursoRepositoryMySql estudianteCursoRepositoryMysql,
                                             EstudiantePensumRepositoryMySql estudiantePensumRepositoryMySql,
                                             CursoRepositoryMySql cursoRepositoryMySql) {
        this.estudianteCursoRepositoryMysql = estudianteCursoRepositoryMysql;
        this.estudiantePensumRepositoryMySql = estudiantePensumRepositoryMySql;
        this.cursoRepositoryMySql = cursoRepositoryMySql;
    }

    @Override
    public EstudianteCursoEntity registrarCurso(EstudianteCursoEntity estudianteCursoEntity, Materia materia) {
        System.out.println("paso1");
        List<EstudiantePensumEntity> estudiantePensums=estudiantePensumRepositoryMySql.findByEstudianteAndPensum
                (estudianteCursoEntity.getEstudiante(),materia.getPensum());
        System.out.println("paso2");
      if(estudiantePensums.size()==0){
          throw new NotFoundItemsInException("No estas registrado en el pensum del curso que quieres matricular");
      }
        System.out.println("paso3");
        Optional<CursoEntity> cursoEntityOptional=cursoRepositoryMySql.findByIdAndMateria
                (estudianteCursoEntity.getCurso().getId(),materia);
        System.out.println("paso4");
      if(!cursoEntityOptional.isPresent()){
          throw new NotFoundItemsInException("El curso y la materia no coinciden");
      }
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
}

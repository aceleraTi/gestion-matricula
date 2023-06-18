package com.acelerati.gestionmatricula.infraestructure.driven_adapters;

import com.acelerati.gestionmatricula.domain.model.Materia;
import com.acelerati.gestionmatricula.domain.model.Profesor;
import com.acelerati.gestionmatricula.domain.model.repository.CursoRepository;
import com.acelerati.gestionmatricula.infraestructure.driven_adapters.interfaces.CursoRepositoryMySql;
import com.acelerati.gestionmatricula.infraestructure.entitys.CursoEntity;
import com.acelerati.gestionmatricula.infraestructure.entitys.SemestreAcademicoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;


public class CursoImpRepositoryMySql implements CursoRepository {

    private final CursoRepositoryMySql cursoRepositoryMySql;

    public CursoImpRepositoryMySql(CursoRepositoryMySql cursoRepositoryMySql) {
        this.cursoRepositoryMySql = cursoRepositoryMySql;
    }

    @Override
    public CursoEntity save(CursoEntity cursoEntity) {
        return cursoRepositoryMySql.save(cursoEntity);
    }

    @Override
    public CursoEntity update(CursoEntity cursoEntity) {
        return cursoRepositoryMySql.save(cursoEntity);
    }
    @Override
    public Optional<CursoEntity> findById(Long id) {
        return cursoRepositoryMySql.findById(id);

    }

    @Override
    public Page<CursoEntity> findByProfesor(Profesor profesor, Pageable pageable) {
      return cursoRepositoryMySql.findByProfesor(profesor,pageable);
    }

    @Override
    public List<CursoEntity> listCursos(Materia materia){
        return cursoRepositoryMySql.findByMateria(materia);
    }



    public boolean esGrupoUnicoMateriaSemetre(Integer grupo, Materia materia, SemestreAcademicoEntity semestreAcademicoEntity){
        return cursoRepositoryMySql.countByGrupoAndMateriaAndSemestreAcademicoEntity
                (grupo,materia,semestreAcademicoEntity)==0;
    }

    @Override
    public Optional<CursoEntity> findByIdAndProfesorAndEstado(Long id, Profesor profesor, String estado) {
        return cursoRepositoryMySql.findByIdAndProfesorAndEstado(id,profesor,estado);
    }

    public boolean countProfesorCurso(CursoEntity cursoEntity){
        return (cursoEntity.getProfesor() != null)
                ? cursoRepositoryMySql.countByProfesorAndEstado(cursoEntity.getProfesor(), "En Curso") < 4: true;
    }


}

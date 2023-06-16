package com.acelerati.gestionmatricula.infraestructure.driven_adapters;

import com.acelerati.gestionmatricula.domain.model.Materia;
import com.acelerati.gestionmatricula.domain.model.Profesor;
import com.acelerati.gestionmatricula.infraestructure.driven_adapters.interfaces.jpa_repository.CursoRepository;
import com.acelerati.gestionmatricula.infraestructure.driven_adapters.interfaces.CursoRepositoryMySql;
import com.acelerati.gestionmatricula.infraestructure.entitys.CursoEntity;
import com.acelerati.gestionmatricula.infraestructure.entitys.SemestreAcademicoEntity;
import com.acelerati.gestionmatricula.infraestructure.exceptions.NotCreatedInException;
import com.acelerati.gestionmatricula.infraestructure.exceptions.NotFoundItemsInException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class CursoImpRepositoryMySql implements CursoRepository {

    private final CursoRepositoryMySql cursoRepositoryMySql;


    public CursoImpRepositoryMySql(CursoRepositoryMySql cursoRepositoryMySql) {
        this.cursoRepositoryMySql = cursoRepositoryMySql;
    }

    @Override
    public CursoEntity save(CursoEntity cursoEntity) {

        if(esGrupoUnicoMateriaSemetre(cursoEntity.getGrupo(),
                cursoEntity.getMateria(),cursoEntity.getSemestreAcademicoEntity())){

            System.out.println(countProfesorCurso(cursoEntity));
            if(countProfesorCurso(cursoEntity)){
                return cursoRepositoryMySql.save(cursoEntity);
            }
            throw new NotCreatedInException("El profesor ya tiene los 4 cursos permitidos en curso");
        }
        else{
            System.out.println("Este grupo ya fue asignado");
            throw new NotCreatedInException("El grupo para el semestre y materia ya existe");
        }
    }

    @Override
    public CursoEntity update(CursoEntity cursoEntity) {
        if(countProfesorCurso(cursoEntity) ||cursoEntity.getEstado().equalsIgnoreCase("Cerrado")){
            return cursoRepositoryMySql.save(cursoEntity);
        }
        throw new NotCreatedInException("El profesor ya tiene los 4 cursos permitidos en curso");
    }


    @Override
    public CursoEntity findById(Long id) {
        Optional<CursoEntity> cursoEntityOptional=cursoRepositoryMySql.findById(id);

        if (cursoEntityOptional.isPresent()){
            return cursoEntityOptional.get();
        }
        throw new NotFoundItemsInException("El curso no existe");
    }

    @Override
    public Page<CursoEntity> findByProfesor(Profesor profesor, Pageable pageable) {
        Page<CursoEntity> pageCursoEntity=cursoRepositoryMySql.findByProfesor(profesor,pageable);
        if(pageCursoEntity.getSize()>0){
            return pageCursoEntity;
        }
        throw new NotFoundItemsInException("No se encontraron cursos asignados");
    }

    @Override
    public List<CursoEntity> listCursos(Materia materia){
        return cursoRepositoryMySql.findByMateria(materia);
    }



    private boolean esGrupoUnicoMateriaSemetre(Integer grupo, Materia materia, SemestreAcademicoEntity semestreAcademicoEntity){
        return cursoRepositoryMySql.countByGrupoAndMateriaAndSemestreAcademicoEntity
                (grupo,materia,semestreAcademicoEntity)==0;
    }

    private boolean countProfesorCurso(CursoEntity cursoEntity){

        if(cursoEntity.getProfesor()!=null){
            return cursoRepositoryMySql.countByProfesorAndEstado(cursoEntity.getProfesor(),"En Curso")<4;
        }
        else
        {
            return true;
        }

    }


}

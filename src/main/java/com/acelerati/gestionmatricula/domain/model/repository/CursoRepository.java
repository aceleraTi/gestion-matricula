package com.acelerati.gestionmatricula.domain.model.repository;


import com.acelerati.gestionmatricula.domain.model.Materia;
import com.acelerati.gestionmatricula.domain.model.Profesor;
import com.acelerati.gestionmatricula.infraestructure.entitys.CursoEntity;
import com.acelerati.gestionmatricula.infraestructure.entitys.SemestreAcademicoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CursoRepository {
    CursoEntity save(CursoEntity cursoEntity);
    CursoEntity update(CursoEntity cursoEntity);
    CursoEntity findById(Long id);
    Page<CursoEntity> findByProfesor(Profesor profesor, Pageable pageable);

    List<CursoEntity> listCursos(Materia materia);
    boolean countProfesorCurso(CursoEntity cursoEntity);
    boolean esGrupoUnicoMateriaSemetre(Integer grupo, Materia materia, SemestreAcademicoEntity semestreAcademicoEntity);


}

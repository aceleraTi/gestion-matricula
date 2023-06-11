package com.acelerati.gestionmatricula.domain.persistence;


import com.acelerati.gestionmatricula.infraestructure.entitys.CursoEntity;
import com.acelerati.gestionmatricula.infraestructure.entitys.SemestreAcademicoEntity;

public interface CursoRepository {

    CursoEntity save(CursoEntity cursoEntity);

    boolean esGrupoUnicoMateriaSemetre(Integer grupo, Long idMateria, SemestreAcademicoEntity semestreAcademicoEntity);




}

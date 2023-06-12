package com.acelerati.gestionmatricula.application.config;

import com.acelerati.gestionmatricula.domain.persistence.EstudianteCursoRepository;
import com.acelerati.gestionmatricula.infraestructure.adapters.EstudianteCursoImpRepositoryMySql;
import com.acelerati.gestionmatricula.infraestructure.adapters.interfaces.CursoRepositoryMySql;
import com.acelerati.gestionmatricula.infraestructure.adapters.interfaces.EstudianteCursoRepositoryMySql;
import com.acelerati.gestionmatricula.infraestructure.adapters.interfaces.EstudiantePensumRepositoryMySql;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EstudianteCursoConfiguration {
    @Bean
    public EstudianteCursoRepository instanciaEstudianteCursoRepositoryMySql
            (EstudianteCursoRepositoryMySql estudianteCursoRepositoryMySql,
             EstudiantePensumRepositoryMySql estudiantePensumRepositoryMySql,
             CursoRepositoryMySql cursoRepositoryMySql){
        return new EstudianteCursoImpRepositoryMySql(estudianteCursoRepositoryMySql, estudiantePensumRepositoryMySql,
                cursoRepositoryMySql);
    }
}

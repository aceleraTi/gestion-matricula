package com.acelerati.gestionmatricula.application.config;

import com.acelerati.gestionmatricula.infraestructure.driven_adapters.persistence.interfaces.jpa_repository.EstudianteCursoRepository;
import com.acelerati.gestionmatricula.infraestructure.driven_adapters.persistence.EstudianteCursoImpRepositoryMySql;
import com.acelerati.gestionmatricula.infraestructure.driven_adapters.persistence.interfaces.CursoRepositoryMySql;
import com.acelerati.gestionmatricula.infraestructure.driven_adapters.persistence.interfaces.EstudianteCursoRepositoryMySql;
import com.acelerati.gestionmatricula.infraestructure.driven_adapters.persistence.interfaces.EstudiantePensumRepositoryMySql;
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

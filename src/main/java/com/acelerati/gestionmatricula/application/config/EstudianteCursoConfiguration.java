package com.acelerati.gestionmatricula.application.config;

import com.acelerati.gestionmatricula.infraestructure.driven_adapters.interfaces.jpa_repository.EstudianteCursoRepository;
import com.acelerati.gestionmatricula.infraestructure.driven_adapters.EstudianteCursoImpRepositoryMySql;
import com.acelerati.gestionmatricula.infraestructure.driven_adapters.interfaces.CursoRepositoryMySql;
import com.acelerati.gestionmatricula.infraestructure.driven_adapters.interfaces.EstudianteCursoRepositoryMySql;
import com.acelerati.gestionmatricula.infraestructure.driven_adapters.interfaces.EstudiantePensumRepositoryMySql;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EstudianteCursoConfiguration {
    @Bean
    public EstudianteCursoRepository instanciaEstudianteCursoRepositoryMySql
            (EstudianteCursoRepositoryMySql estudianteCursoRepositoryMySql){
        return new EstudianteCursoImpRepositoryMySql(estudianteCursoRepositoryMySql);
    }
}

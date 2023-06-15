package com.acelerati.gestionmatricula.application.config;

import com.acelerati.gestionmatricula.infraestructure.driven_adapters.persistence.interfaces.jpa_repository.CursoRepository;
import com.acelerati.gestionmatricula.infraestructure.driven_adapters.persistence.CursoImpRepositoryMySql;
import com.acelerati.gestionmatricula.infraestructure.driven_adapters.persistence.interfaces.CursoRepositoryMySql;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CursoConfiguration {

    @Bean
    public CursoRepository instanciaCursoRepositoryMySql(CursoRepositoryMySql cursoRepositoryMySql){
        return new CursoImpRepositoryMySql(cursoRepositoryMySql);
    }


}

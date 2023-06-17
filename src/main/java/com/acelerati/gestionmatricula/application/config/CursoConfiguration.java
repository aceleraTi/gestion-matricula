package com.acelerati.gestionmatricula.application.config;

import com.acelerati.gestionmatricula.domain.model.repository.CursoRepository;
import com.acelerati.gestionmatricula.infraestructure.driven_adapters.CursoImpRepositoryMySql;
import com.acelerati.gestionmatricula.infraestructure.driven_adapters.interfaces.CursoRepositoryMySql;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CursoConfiguration {

    @Bean
    public CursoRepository instanciaCursoRepositoryMySql(CursoRepositoryMySql cursoRepositoryMySql){
        return new CursoImpRepositoryMySql(cursoRepositoryMySql);
    }


}

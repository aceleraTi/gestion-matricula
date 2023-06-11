package com.acelerati.gestionmatricula.application.config;

import com.acelerati.gestionmatricula.domain.persistence.CursoRepository;
import com.acelerati.gestionmatricula.infraestructure.adapters.CursoImpRepositoryMysql;
import com.acelerati.gestionmatricula.infraestructure.adapters.interfaces.CursoRepositoryMySql;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CursoConfiguration {

    @Bean
    public CursoRepository instanciaCursoRepositoryMySql(CursoRepositoryMySql cursoRepositoryMySql){
        return new CursoImpRepositoryMysql(cursoRepositoryMySql);
    }


}

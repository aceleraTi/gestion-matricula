package com.acelerati.gestionmatricula.application.config;

import com.acelerati.gestionmatricula.domain.persistence.TareaRepository;
import com.acelerati.gestionmatricula.infraestructure.adapters.TareaImpRepositoryMySql;
import com.acelerati.gestionmatricula.infraestructure.adapters.interfaces.CursoRepositoryMySql;
import com.acelerati.gestionmatricula.infraestructure.adapters.interfaces.TareaRepositoryMySql;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TareaConfiguration {
    @Bean
    public TareaRepository instanciaTareaRepositoryMySql(TareaRepositoryMySql tareaRepositoryMySql,
                                                         CursoRepositoryMySql cursoRepositoryMySql){
        return new TareaImpRepositoryMySql(tareaRepositoryMySql, cursoRepositoryMySql);
    }

}

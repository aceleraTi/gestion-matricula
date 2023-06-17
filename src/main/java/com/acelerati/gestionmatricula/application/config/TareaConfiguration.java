package com.acelerati.gestionmatricula.application.config;

import com.acelerati.gestionmatricula.domain.model.repository.TareaRepository;
import com.acelerati.gestionmatricula.infraestructure.driven_adapters.TareaImpRepositoryMySql;
import com.acelerati.gestionmatricula.infraestructure.driven_adapters.interfaces.CursoRepositoryMySql;
import com.acelerati.gestionmatricula.infraestructure.driven_adapters.interfaces.TareaRepositoryMySql;
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

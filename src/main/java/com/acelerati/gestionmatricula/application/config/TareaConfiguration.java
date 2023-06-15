package com.acelerati.gestionmatricula.application.config;

import com.acelerati.gestionmatricula.infraestructure.driven_adapters.persistence.interfaces.jpa_repository.TareaRepository;
import com.acelerati.gestionmatricula.infraestructure.driven_adapters.persistence.TareaImpRepositoryMySql;
import com.acelerati.gestionmatricula.infraestructure.driven_adapters.persistence.interfaces.CursoRepositoryMySql;
import com.acelerati.gestionmatricula.infraestructure.driven_adapters.persistence.interfaces.TareaRepositoryMySql;
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

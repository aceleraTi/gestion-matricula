package com.acelerati.gestionmatricula.application.config;

import com.acelerati.gestionmatricula.domain.model.repository.EstudiantePensumRepository;
import com.acelerati.gestionmatricula.infraestructure.driven_adapters.EstudiantePensumImpRepositoryMySql;
import com.acelerati.gestionmatricula.infraestructure.driven_adapters.interfaces.EstudiantePensumRepositoryMySql;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EstudiantePensumConfiguration {

    @Bean
    public EstudiantePensumRepository instanciaEstudiantePensumRepositoryMySql(EstudiantePensumRepositoryMySql estudiantePensumRepositoryMySql){
        return new EstudiantePensumImpRepositoryMySql(estudiantePensumRepositoryMySql);
    }

}

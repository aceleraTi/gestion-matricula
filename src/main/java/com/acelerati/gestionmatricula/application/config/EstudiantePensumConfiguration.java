package com.acelerati.gestionmatricula.application.config;

import com.acelerati.gestionmatricula.infraestructure.driven_adapters.persistence.interfaces.jpa_repository.EstudiantePensumRepository;
import com.acelerati.gestionmatricula.infraestructure.driven_adapters.persistence.EstudiantePensumImpRepositoryMySql;
import com.acelerati.gestionmatricula.infraestructure.driven_adapters.persistence.interfaces.EstudiantePensumRepositoryMySql;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EstudiantePensumConfiguration {

    @Bean
    public EstudiantePensumRepository instanciaEstudiantePensumRepositoryMySql(EstudiantePensumRepositoryMySql estudiantePensumRepositoryMySql){
        return new EstudiantePensumImpRepositoryMySql(estudiantePensumRepositoryMySql);
    }

}

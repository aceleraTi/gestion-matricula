package com.acelerati.gestionmatricula.application.config;


import com.acelerati.gestionmatricula.infraestructure.driven_adapters.persistence.interfaces.jpa_repository.EstudianteCursoTareaRepository;
import com.acelerati.gestionmatricula.infraestructure.driven_adapters.persistence.EstudianteCursoTareaImpRepositoryMySql;
import com.acelerati.gestionmatricula.infraestructure.driven_adapters.persistence.interfaces.EstudianteCursoTareaRepositoryMySql;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EstudianteCursoTareaConfiguration {
    @Bean
    public EstudianteCursoTareaRepository instanciaEstudianteCursoTareaRepositoryMySql
            (EstudianteCursoTareaRepositoryMySql estudianteCursoTareaRepositoryMySql){
        return new EstudianteCursoTareaImpRepositoryMySql(estudianteCursoTareaRepositoryMySql);
    }
}

package com.acelerati.gestionmatricula.application.config;


import com.acelerati.gestionmatricula.domain.persistence.EstudianteCursoTareaRepository;
import com.acelerati.gestionmatricula.infraestructure.adapters.EstudianteCursoTareaImpRepositoryMySql;
import com.acelerati.gestionmatricula.infraestructure.adapters.interfaces.EstudianteCursoTareaRepositoryMySql;
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

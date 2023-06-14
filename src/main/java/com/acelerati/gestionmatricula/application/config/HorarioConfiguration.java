package com.acelerati.gestionmatricula.application.config;

import com.acelerati.gestionmatricula.domain.persistence.HorarioRepository;
import com.acelerati.gestionmatricula.infraestructure.adapters.HorarioImpRepositoryMySql;
import com.acelerati.gestionmatricula.infraestructure.adapters.interfaces.HorarioRepositoryMySql;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HorarioConfiguration {
    @Bean
    public HorarioRepository instanciaHorarioRepositoryMySql(HorarioRepositoryMySql horarioRepositoryMySql){
        return new HorarioImpRepositoryMySql(horarioRepositoryMySql);
    }

}

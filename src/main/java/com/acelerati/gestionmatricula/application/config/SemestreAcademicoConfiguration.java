package com.acelerati.gestionmatricula.application.config;

import com.acelerati.gestionmatricula.infraestructure.driven_adapters.persistence.interfaces.jpa_repository.SemestreAcademicoRepository;
import com.acelerati.gestionmatricula.infraestructure.driven_adapters.persistence.SemestreAcademicoImpRepositoryMySql;
import com.acelerati.gestionmatricula.infraestructure.driven_adapters.persistence.interfaces.SemestreAcademicoRepositoryMySql;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SemestreAcademicoConfiguration {

    @Bean
    public SemestreAcademicoRepository instanciaSemestreAcademicoRepositoryMySql(SemestreAcademicoRepositoryMySql semestreAcademicoRepositoryMySql){
       return new SemestreAcademicoImpRepositoryMySql(semestreAcademicoRepositoryMySql);
    }


}

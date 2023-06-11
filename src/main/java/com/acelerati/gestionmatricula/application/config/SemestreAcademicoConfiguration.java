package com.acelerati.gestionmatricula.application.config;

import com.acelerati.gestionmatricula.domain.persistence.SemestreAcademicoRepository;
import com.acelerati.gestionmatricula.infraestructure.adapters.SemestreAcademicoImpRepositoryMySql;
import com.acelerati.gestionmatricula.infraestructure.adapters.interfaces.SemestreAcademicoRepositoryMySql;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SemestreAcademicoConfiguration {

    @Bean
    public SemestreAcademicoRepository instanciaSemestreAcademicoRepositoryMySql(SemestreAcademicoRepositoryMySql semestreAcademicoRepositoryMySql){
       return new SemestreAcademicoImpRepositoryMySql(semestreAcademicoRepositoryMySql);
    }


}

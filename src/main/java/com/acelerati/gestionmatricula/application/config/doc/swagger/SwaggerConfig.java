package com.acelerati.gestionmatricula.application.config.doc.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket apiDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.acelerati.gestionmatricula.infraestructure.rest.controllers"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(getApiInfo());
    }

    private ApiInfo getApiInfo() {
        return new ApiInfo(
                "Matricula Service API",
                "Servicios para la gestión de matriculas de MegaRed",
                "1.0",
                "https://www.pragma.com.co/es/terminos-condiciones",
                new Contact("PRAGMA", "https://www.pragma.com.co/es", "https://www.pragma.com.co/es"),
                "LICENSE",
                "LICENSE URL",
                Collections.emptyList()
        );
    }
}

package com.acelerati.gestionmatricula.application.config.consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestConsumerConfiguration {


  @Bean()
   public RestTemplate getRestTemplate(){
       return new RestTemplate();
    }


}

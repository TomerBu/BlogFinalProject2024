package edu.tomerbu.blogfinalproject2024.config;


import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    ModelMapper getModelMapper(){
        //create a
        return new ModelMapper();
    }
}

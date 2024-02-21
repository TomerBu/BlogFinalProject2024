package edu.tomerbu.blogfinalproject2024;

import edu.tomerbu.blogfinalproject2024.config.ArikProps;
import edu.tomerbu.blogfinalproject2024.config.RSAKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan(basePackageClasses = {RSAKeyProperties.class, ArikProps.class})
public class BlogFinalProject2024Application {

    public static void main(String[] args) {
        SpringApplication.run(BlogFinalProject2024Application.class, args);
    }

}

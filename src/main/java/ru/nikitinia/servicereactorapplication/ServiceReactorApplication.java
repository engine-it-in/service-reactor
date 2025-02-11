package ru.nikitinia.servicereactorapplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import reactivefeign.spring.config.EnableReactiveFeignClients;

@SpringBootApplication
@ConfigurationPropertiesScan
@EnableReactiveFeignClients
public class ServiceReactorApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceReactorApplication.class, args);
    }

}

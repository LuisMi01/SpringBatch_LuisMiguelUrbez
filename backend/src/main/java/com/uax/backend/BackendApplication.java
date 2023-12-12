package com.uax.backend;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;

@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(MongoTemplate mongoTemplate) {
        return args -> {
            System.out.println("Conectando a la base de datos..");
            mongoTemplate.getDb();
            System.out.println("Mongodb conexion: " + true);
        };
    }

}

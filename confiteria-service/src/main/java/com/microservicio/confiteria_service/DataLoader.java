package com.microservicio.confiteria_service;

import java.util.Random;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.microservicio.confiteria_service.model.Confiteria;
import com.microservicio.confiteria_service.repository.ConfiteriaRepository;

import net.datafaker.Faker;

@Component
public class DataLoader implements CommandLineRunner {
    private final ConfiteriaRepository confiteriaRepository;

    DataLoader(ConfiteriaRepository confiteriaRepository) {
        this.confiteriaRepository = confiteriaRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();
        Random random = new Random();
        // Generar confiterias
        for (int i = 0; i < 10; i++) {
            Confiteria confi = new Confiteria();
            confi.setNombre(faker.options().option("Cabritas Pequeñas","Cabritas Medianas", "Cabritas Grandes", "Bebida mediana"));
            confi.setDescripcion(faker.options().option("Perfectas para degustar durante la funcion"));
            confi.setPrecio(faker.number().numberBetween(4990, 9990));
            confi.setDisponibilidad(random.nextBoolean());
            confiteriaRepository.save(confi);
        }
    }
}
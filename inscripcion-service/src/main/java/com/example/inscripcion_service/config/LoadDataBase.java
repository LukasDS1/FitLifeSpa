package com.example.inscripcion_service.config;



import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.example.inscripcion_service.repository.InscripcionRepository;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class LoadDataBase {

    @Bean
    CommandLineRunner initDataBase(InscripcionRepository inscRepo) {
        return args -> {
            if (inscRepo.count() == 0) {
                System.out.println("Datos cargados correctamente");
            } else {
                System.out.println("Datos ya existen. No se cargaron.");
            }
        };
    }
}

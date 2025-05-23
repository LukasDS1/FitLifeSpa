package com.example.inscripcion_service.config;

import java.util.ArrayList;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.inscripcion_service.model.Clase;
import com.example.inscripcion_service.model.Estado;

import com.example.inscripcion_service.repository.ClaseRepository;
import com.example.inscripcion_service.repository.EstadoRepository;
import com.example.inscripcion_service.repository.InscripcionRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class LoadDataBase {

    @Bean
    CommandLineRunner initDataBase(InscripcionRepository inscRepo, ClaseRepository claseRepo, EstadoRepository estadoRepo) {
        return args -> {
            if (inscRepo.count() == 0 && estadoRepo.count() == 0 && claseRepo.count() == 0) {
                


                Estado activo= new Estado(null, "Activo", new ArrayList<>());
                Estado inactivo = new Estado(null, "Inactivo", new ArrayList<>());

                Clase clase1 = new Clase(null, "Piernas", new ArrayList<>());
                Clase clase2 = new Clase(null, "Brazos", new ArrayList<>());

            
                estadoRepo.save(activo);
                estadoRepo.save(inactivo);
                
                claseRepo.save(clase1);
                claseRepo.save(clase2);


                System.out.println("Datos cargados correctamente");

            } else {
                System.out.println("Datos ya existen. No se cargaron.");
            }
        };
    }
}

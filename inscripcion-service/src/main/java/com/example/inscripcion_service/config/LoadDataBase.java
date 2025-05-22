package com.example.inscripcion_service.config;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.inscripcion_service.model.Clase;
import com.example.inscripcion_service.model.Estado;
import com.example.inscripcion_service.model.Inscripcion;
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
                
                Calendar cal1 = Calendar.getInstance();
                cal1.set(2025, Calendar.MARCH, 14); // 14 de marzo de 2025
                Date fecha1 = cal1.getTime();

                Calendar cal2 = Calendar.getInstance();
                cal2.set(2025, Calendar.JANUARY, 7); // 7 de octubre de 2025
                Date fecha2 = cal2.getTime();


                Estado activo= new Estado(null, "Activo", new ArrayList<>());
                Estado inactivo = new Estado(null, "Inactivo", new ArrayList<>());

                Clase clase1 = new Clase(null, "Piernas", new ArrayList<>());
                Clase clase2 = new Clase(null, "Brazos", new ArrayList<>());

                Inscripcion inscrip1 = new Inscripcion(null, fecha1, clase1, activo);
                Inscripcion inscrip2 = new Inscripcion(null, fecha2, clase2, inactivo);

                estadoRepo.save(activo);
                estadoRepo.save(inactivo);
                
                claseRepo.save(clase1);
                claseRepo.save(clase2);

                inscRepo.save(inscrip1);
                inscRepo.save(inscrip2);

                System.out.println("Datos cargados correctamente");

            } else {
                System.out.println("Datos ya existen. No se cargaron.");
            }
        };
    }
}

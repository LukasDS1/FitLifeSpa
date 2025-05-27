package com.example.soporteservice.config;

import java.util.ArrayList;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.example.soporteservice.model.Motivo;
import com.example.soporteservice.repository.MotivoRepository;


import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class LoadToDataBase {

        @Bean
        CommandLineRunner initDatabase(
        MotivoRepository motivoRepository) {
    return args -> {
        if ( motivoRepository.count() == 0) {

            Motivo preguntas = new Motivo(null, "Preguntas.", new ArrayList<>());
            motivoRepository.save(preguntas);

            Motivo solicitudServicio = new Motivo(null, "Solicitud de Servicio.", new ArrayList<>());
            motivoRepository.save(solicitudServicio);

            Motivo incidentesSeguridad = new Motivo(null, "Incidentes de Seguridad.", new ArrayList<>());
            motivoRepository.save(incidentesSeguridad);

            Motivo sugerencias = new Motivo(null, "Sugerencias.", new ArrayList<>());
            motivoRepository.save(sugerencias);

            Motivo reclamos = new Motivo(null, "Reclamos.", new ArrayList<>());
            motivoRepository.save(reclamos);


        } else {
            System.out.println("Datos ya existentes, no se cargaron");
        }
    };
}



}

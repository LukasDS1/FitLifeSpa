package com.example.soporteservice.config;

import java.util.ArrayList;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.soporteservice.model.Motivo;
import com.example.soporteservice.repository.EstadoRepository;
import com.example.soporteservice.repository.HistorialRepository;
import com.example.soporteservice.repository.MotivoRepository;
import com.example.soporteservice.repository.RolRepository;
import com.example.soporteservice.repository.TicketRepository;
import com.example.soporteservice.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class LoadToDataBase {

        @Bean
        CommandLineRunner initDatabase(
        UsuarioRepository usuarioRepository,
        RolRepository rolRepository,
        EstadoRepository estadoRepository,
        HistorialRepository historialRepository,
        TicketRepository ticketRepository,
        MotivoRepository motivoRepository) {
    return args -> {
        if (usuarioRepository.count() == 0 && rolRepository.count() == 0 &&estadoRepository.count() == 0 && historialRepository.count() == 0 && ticketRepository.count() == 0 && motivoRepository.count() == 0) {

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

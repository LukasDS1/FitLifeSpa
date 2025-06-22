package com.example.soporteservice.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.soporteservice.model.Historial;
import com.example.soporteservice.model.Motivo;
import com.example.soporteservice.model.Ticket;
import com.example.soporteservice.repository.HistorialRepository;
import com.example.soporteservice.repository.MotivoRepository;
import com.example.soporteservice.repository.TicketRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class LoadToDataBase {

        @Bean
        CommandLineRunner initDatabase(
        MotivoRepository motivoRepository,TicketRepository ticketRepository,HistorialRepository historialRepository) {
    return args -> {
        if ( motivoRepository.count() == 0 && ticketRepository.count() == 0 && historialRepository.count() == 0) {

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

            Ticket ticket = new Ticket(null, new Date(), 4L, 1L,5L , new ArrayList<>(), reclamos);
            Historial historial = new Historial(null,"Problemas con el pago de las membresias", "PROBLEMAS", new Date(), ticket);
            ticket.getHistorial().add(historial);
      
            Ticket ticket2 = new Ticket(null, new Date(), 4L, 1L,6L , new ArrayList<>(), sugerencias);
            Historial historial2 = new Historial(null,"Problemas con las membresias", "PROBLEMAS", new Date(), ticket);
            ticket2.getHistorial().add(historial2);
            

            ticketRepository.save(ticket);
            ticketRepository.save(ticket2);


        } else {
            System.out.println("Datos ya existentes, no se cargaron");
        }
    };
}



}

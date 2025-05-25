package com.example.soporteservice.config;

import java.util.ArrayList;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.soporteservice.model.Estado;
import com.example.soporteservice.model.Historial;
import com.example.soporteservice.model.Motivo;
import com.example.soporteservice.model.Rol;
import com.example.soporteservice.model.Ticket;
import com.example.soporteservice.model.Usuario;
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
    //TODO:CAMBIAR TIDO DE DATE PARA QUE SEA COMO SYSDATE O COMO DATE DEPENDIENDO DE 
    //TODO:BORRAR USUARIO Y ROL SOLO ESTAN DE PRUEBA y los repositorios de usuario etc
    private final PasswordEncoder passwordEncoder;
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
            Rol cliente = new Rol(null, "Cliente", new ArrayList<>());
            Rol soporte = new Rol(null, "Soporte", new ArrayList<>());
            rolRepository.save(cliente);
            rolRepository.save(soporte);
  
            Usuario usuario1 = new Usuario(null, "prueba@gmail.com", "null", "LUKAS", "Donsoso", "lol", "null", "12345678-9", new ArrayList<>(), cliente);
            usuario1.setPassword(passwordEncoder.encode(usuario1.getPassword()));
            usuarioRepository.save(usuario1);

            Usuario usuario2 = new Usuario(null, "prueba2@gmail.com", "null", "soportito", "soportoso", "lol", "null", "9876543-2", new ArrayList<>(), soporte);
            usuario2.setPassword(passwordEncoder.encode(usuario2.getPassword()));
            usuarioRepository.save(usuario2);

            Estado activo = new Estado(null, "Activo", new ArrayList<>());
            Estado inactivo = new Estado(null, "Inactivo", new ArrayList<>());
            estadoRepository.save(activo);
            estadoRepository.save(inactivo);

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

            Ticket ticket1 = new Ticket(null,new java.sql.Date(System.currentTimeMillis()),null,new ArrayList<>(),preguntas,activo,usuario1);
            ticketRepository.save(ticket1); 

            activo.getTicket().add(ticket1);
            preguntas.getTicket().add(ticket1);
            estadoRepository.save(activo);
            motivoRepository.save(preguntas);

            Historial historial = new Historial(null, "Hola", "consulta",new java.sql.Date(System.currentTimeMillis()), ticket1);
            historialRepository.save(historial);

        } else {
            System.out.println("Datos ya existentes, no se cargaron");
        }
    };
}



}

package com.example.privileges_service.config;

import java.util.ArrayList;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.example.privileges_service.model.Estado;
import com.example.privileges_service.model.Modulo;
import com.example.privileges_service.model.Privileges;
import com.example.privileges_service.repository.EstadoRepository;
import com.example.privileges_service.repository.ModuloRepository;
import com.example.privileges_service.repository.PrivilegesRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class LoadDataBase {

    @Bean
    CommandLineRunner initDataBase(EstadoRepository estadoRepo, PrivilegesRepository privRepo, ModuloRepository modRepo) {
        return args -> {
            if (estadoRepo.count() == 0 && privRepo.count() == 0 && modRepo.count() == 0) {
                
                Estado activo = new Estado(null, "Activo", new ArrayList<>());
                estadoRepo.save(activo);

                Estado inactivo = new Estado (null, "Inactivo", new ArrayList<>());
                estadoRepo.save(inactivo);

                Modulo UserManagement = new Modulo(null, "UserManagement", new ArrayList<>());
                modRepo.save(UserManagement);

                Modulo reserva = new Modulo(null,"Reserva", new ArrayList<>());
                modRepo.save(reserva);

                Privileges delete = new Privileges(null,null,activo,UserManagement);
                privRepo.save(delete);

                Privileges reservar = new Privileges(null, null, activo, reserva);
                privRepo.save(reservar);

                System.out.println("Datos cargados correctamente");

            } else {
                System.out.println("Datos ya existen. No se cargaron.");
            }
        };
    }
}

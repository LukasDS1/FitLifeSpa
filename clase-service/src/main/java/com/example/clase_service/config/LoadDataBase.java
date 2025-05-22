package com.example.clase_service.config;

import java.util.ArrayList;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.clase_service.model.Clase;
import com.example.clase_service.model.Servicio;
import com.example.clase_service.repository.ClaseRepository;
import com.example.clase_service.repository.ServicioRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class LoadDataBase {
    
    @Bean
    CommandLineRunner initDatabase(ClaseRepository claseRepository,ServicioRepository servicioRepository){
        return args ->{
            if(claseRepository.count() == 0 && servicioRepository.count() ==0){
                Clase Pilates = new Clase(null,"Clase de pilates",new java.sql.Date(System.currentTimeMillis()),"Clase de pilates",null);
                claseRepository.save(Pilates);

                Servicio Masaje = new Servicio(null,"Masaje","Masajes profesionales sona trasera",new ArrayList<>());
                servicioRepository.save(Masaje);

                Masaje.getClases().add(Pilates);
               
               

            }else{
                System.out.println("Datos ya existen.No se cargaron");
            }
        };
    }

}
